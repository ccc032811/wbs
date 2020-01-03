package com.neefull.fsp.web.qff.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.api.R;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.qff.entity.ImageQuery;
import com.neefull.fsp.web.qff.service.IDateImageService;
import com.neefull.fsp.web.qff.service.IProcessService;
import com.neefull.fsp.web.system.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: chengchengchu
 * @Date: 2019/12/16  16:23
 */

@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    @Autowired
    private IDateImageService dateImageService;
    @Autowired
    private IProcessService processService;

    private static final String IMAGE_DIR ="D:\\JavaSoft\\nginx-1.12.2\\html";


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
        return new FebsResponse().success();
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

        String paths[] = IMAGE_DIR.split(StringPool.SLASH);
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
        }
        File filePath = new File(IMAGE_DIR, filename);
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

}
