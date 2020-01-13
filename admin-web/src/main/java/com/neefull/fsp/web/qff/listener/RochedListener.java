package com.neefull.fsp.web.qff.listener;

import com.neefull.fsp.web.qff.config.SendMailProperties;
import com.neefull.fsp.web.qff.config.TemplateProperties;
import com.neefull.fsp.web.qff.entity.Commodity;
import com.neefull.fsp.web.qff.entity.Recent;
import com.neefull.fsp.web.qff.entity.Roche;
import com.neefull.fsp.web.qff.service.IRecentService;
import com.neefull.fsp.web.qff.service.IRocheService;
import com.sun.mail.util.MailSSLSocketFactory;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: chengchengchu
 * @Date: 2019/12/22  19:40
 */

@Service("rochedListener")
public class RochedListener implements JavaDelegate {
    @Autowired
    private SendMailProperties mailProperties;
    @Autowired
    private TemplateProperties templateProperties;
    @Autowired
    private IRocheService rocheService;
  /*  @Autowired
    private FilePdfTemplate template;*/

    @Override
    public void execute(DelegateExecution execution) {
       /* //获取流程中的对象  获取邮件地址跟密码
        User user = (User) execution.getVariable("user");
        String email = user.getEmail();*/

        if(execution.getCurrentActivityName().equals("罗氏")){
            //查询罗氏接受人的邮箱
        }else if(execution.getCurrentActivityName().equals("康德乐")){
            //查询康德乐接受人的邮箱
        }

        //生成pdf和获取附件的地址
        String businessKey = execution.getProcessBusinessKey();
        String starId = "";
        if (businessKey.startsWith(Roche.class.getSimpleName())) {
            if (StringUtils.isNotBlank(businessKey)) {
                //截取字符串
                starId = businessKey.split("\\:")[1].toString();
            }
        }
        Roche roche = rocheService.queryRocheById(Integer.parseInt(starId));

        Map<String,String> map = new HashMap<>();
        //将值放入map中

        //pdf的地址
        String url = templateProperties.getConserveDownLoadPath()+ roche.getNumber()+".pdf";
//        template.createPdf(map,templateProperties.getConserveTemplatePath(),templateProperties.getConserveDownLoadPath(),url);
        //获取图片地址


        //发送带附件的邮件
        sendMai();

    }

    @Async
    public void sendMai(){
        //配置
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
        } catch (GeneralSecurityException e) {
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
            mimeMessageHelper.setSubject("抢购抢购抢购抢购抢购抢购抢购抢购");//邮件名称
            mimeMessageHelper.setText("抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购抢购");//邮箱文字内容
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }
}
