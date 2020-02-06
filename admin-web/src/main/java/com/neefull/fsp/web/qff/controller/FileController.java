package com.neefull.fsp.web.qff.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.qff.aspect.Qff;
import com.neefull.fsp.web.qff.config.ProcessInstanceProperties;
import com.neefull.fsp.web.qff.config.SendMailProperties;
import com.neefull.fsp.web.qff.entity.Recent;
import com.neefull.fsp.web.qff.service.IDateImageService;
import com.neefull.fsp.web.qff.service.IProcessService;
import com.neefull.fsp.web.qff.service.IRecentService;
import com.neefull.fsp.web.qff.utils.MailUtils;
import com.neefull.fsp.web.system.entity.User;
import com.sun.mail.util.MailSSLSocketFactory;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * @Author: chengchengchu
 * @Date: 2019/12/16  16:23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    @Autowired
    private IDateImageService dateImageService;
    @Autowired
    private IProcessService processService;
    @Autowired
    private IRecentService recentService;
    @Autowired
    private ProcessInstanceProperties properties;

    private static final Integer SELECT_NUMBER = 2;


    /**查询需要完成任务
     * @return
     */
    @GetMapping("/findTask")
    public FebsResponse findTask(){
        User user = getCurrentUser();
        Integer count = processService.findTask(user.getUsername());
        return new FebsResponse().success().data(count);
    }

    /**查询所有图片
     * @param dataId
     * @param relevance
     * @return
     */
    @GetMapping("/findImages/{dataId}/{relevance}")
    public FebsResponse findImageByIdAndForm(@PathVariable Integer dataId ,@PathVariable String relevance){

        List<String> list = dateImageService.findImageByIdAndForm(dataId,relevance);
        return new FebsResponse().success().data(list);
    }

    /**删除图片
     * @param url
     * @return
     */
    @GetMapping("/deleteImage/{url}")
    public FebsResponse deleteImage(@PathVariable String url){
        dateImageService.deleteImage(url);
        return new FebsResponse().success().data("200");
    }

    /**上传图片
     * @param file
     * @return
     * @throws FebsException
     */
    @PostMapping("/uploadImage")
    public FebsResponse uploadImage(@RequestParam("file") MultipartFile file) throws FebsException {

        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new FebsException("图片上传类型有误");
        }
        String filename = file.getOriginalFilename();
        String extension = StringUtils.substringAfterLast(filename, StringPool.DOT);
        filename = UUID.randomUUID().toString() + StringPool.DOT + extension;

        /*String[] paths = properties.getImagePath().split(StringPool.SLASH);
        String dir = paths[0];
        for (int i = 0; i < paths.length - 1; i++) {
            try {
                dir = dir + "/" + paths[i + 1];
                File dirFile = new File(dir);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                    System.out.println("创建目录为：" + dir);
                }
            } catch (Exception err) {
                System.err.println("文件夹创建发生异常");
            }
        }*/
        File filePath = new File(properties.getImagePath(), filename);
        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            throw new FebsException("文件上传失败");
        }
        Map<String,String> map =new HashMap<>();
        map.put("index",UUID.randomUUID().toString().replaceAll(StringPool.DASH,StringPool.EMPTY));
        map.put("value",filename);
        return new FebsResponse().success().data(map);

    }


    /**解析exexl
     * @param file
     * @return
     * @throws FebsException
     */
    @Qff("解析近效期QFF")
    @PostMapping("/resolver")
    @RequiresPermissions("recent:import")
    public FebsResponse uploadExcel(MultipartFile file) throws FebsException {

        List<Recent> list = new ArrayList<>();
        List<Integer> errorList = new ArrayList();

        try {
            ExcelKit.$Import(Recent.class).readXlsx(file.getInputStream(), new ExcelReadHandler<Recent>() {
                @Override
                public void onSuccess(int sheetIndex, int rowIndex, Recent entity) {
                    if(rowIndex > SELECT_NUMBER){
                        list.add(entity);
                        recentService.addRecent(entity);
                        processService.commitProcess(entity,getCurrentUser());
                    }
                }
                @Override
                public void onError(int sheetIndex, int rowIndex, List<ExcelErrorField> errorFields) {
                    errorList.add(rowIndex+1);
                }
            });
        } catch (IOException e) {
            throw new FebsException("导入文件失败");
        }
        if(errorList.size()>0){
            String count = null;
            for (int i=0;i<=errorList.size()-1;i++) {
                if(i==errorList.size()-1){
                    count+=errorList.get(i)+"行";
                }
                count += errorList.get(i) +"行 ,";
            }
            throw new FebsException("导入失败数据,失败行数"+count);
        }
        //发送邮件

        StringBuilder content=new StringBuilder("<html><head></head><body><h3>你好</h3>");
        content.append("<tr><h3>具体详情如下表所示:</h3></tr>");
        content.append("<table border='5' style='border:solid 1px #000;font-size=10px;'>");
        content.append("<tr style='background-color: #00A1DD'><td>康德乐物料号</td>" +
                       "<td>罗氏物料号</td><td>产品物料号</td><td>有效期</td>" +
                       "<td>批号</td><td>SAP批次</td><td>工厂</td><td>库位</td><td>数量</td></tr>");
        for (Recent recent : list) {
            content.append("<tr><td>"+recent.getkMater()+"</td><td>"+recent.getrMater()+"</td>" +
                           "<td>"+recent.getName()+"</td><td>"+recent.getUseLife()+"</td>" +
                           "<td>"+recent.getBatch()+"</td><td>"+recent.getSapBatch()+"</td>" +
                           "<td>"+recent.getFactory()+"</td><td>"+recent.getWareHouse()+"</td><td>"+recent.getNumber()+"</td></tr>");
        }
        content.append("</table>");
        content.append("</body></html>");

        String text = content.toString();
        sendMail(text);

        return new FebsResponse().success();
    }

    @Autowired
    private SendMailProperties mailProperties;

    public  void sendMail(String text) {

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailProperties.getHost());
        javaMailSender.setDefaultEncoding(mailProperties.getCharset());
        javaMailSender.setProtocol(mailProperties.getProtocol());
        javaMailSender.setPort(Integer.parseInt(mailProperties.getPort()));
        javaMailSender.setUsername("ccc032811@163.com");//发送者的邮箱
        javaMailSender.setPassword("ccc032811");//发送者的密码

        Properties prop = new Properties();
        prop.setProperty("mail.smtp.auth", mailProperties.getAuth());
//        prop.setProperty("mail.smtp.timeout", mailProperties.getTimeout());
        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", true);
            prop.put("mail.smtp.ssl.socketFactory", sf);
        } catch (
                GeneralSecurityException e) {
            e.printStackTrace();
        }
        javaMailSender.setJavaMailProperties(prop);

        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            mimeMessageHelper.setFrom("ccc032811@163.com");//发送的邮箱地址
            mimeMessageHelper.setTo("ccc032811@163.com");//接收的邮箱地址
//            mimeMessageHelper.setTo("wangpei_it@163.com");//接收的邮箱地址
//            mimeMessageHelper.setCc("");//抄送者的邮箱地址
            mimeMessageHelper.setSubject("测试Springboot发送带附件的邮件,用来测试的");//邮件名称
            mimeMessageHelper.setText(text,true);//邮箱文字内容

        } catch (
                MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }


}
