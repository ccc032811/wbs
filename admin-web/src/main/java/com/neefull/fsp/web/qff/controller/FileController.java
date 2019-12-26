package com.neefull.fsp.web.qff.controller;

import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author: chengchengchu
 * @Date: 2019/12/16  16:23
 */

@RestController
@RequestMapping("/file")
public class FileController {

    private static final String IMAGE_DIR ="D:\\JavaSoft\\nginx-1.12.2\\html";
    private static final String IMAGE_URL ="http://127.0.0.1:10086/";

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
        String extension = StringUtils.substringAfterLast(filename, ".");
        filename = UUID.randomUUID().toString() + "." + extension;

        String paths[] = IMAGE_DIR.split("\\/");
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
        return new FebsResponse().success().data(IMAGE_URL+filename);
    }

}
