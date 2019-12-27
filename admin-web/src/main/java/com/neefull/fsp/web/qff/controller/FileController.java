package com.neefull.fsp.web.qff.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.qff.service.IDateImageService;
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
public class FileController {

    @Autowired
    private IDateImageService dateImageService;

    private static final String IMAGE_DIR ="D:\\JavaSoft\\nginx-1.12.2\\html";
    private static final String IMAGE_URL ="http://127.0.0.1:10086/";

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
     * @param dataId
     * @param relevance
     * @param url
     * @return
     */
    @PostMapping("/deleteImage/{dataId}/{relevance}/{vest}/{url}")
    public FebsResponse deleteImage(@PathVariable Integer dataId ,
                                    @PathVariable String relevance,
                                    @PathVariable String url,
                                    @PathVariable String vest){
        dateImageService.deleteImage(dataId,relevance,url,vest);
        return new FebsResponse().success();
    }


    /**上传图片
     * @param file
     * @param id
     * @param type
     * @param relevance
     * @return
     * @throws FebsException
     */
    @PostMapping("/uploadImage")
    public FebsResponse uploadImage(@RequestParam("file") MultipartFile file,
                                    @RequestParam("id")Integer id ,
                                    @RequestParam("type")String type,
                                    @RequestParam("relevance")String relevance) throws FebsException {

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
        Map<String,String> map =new HashMap<>();
        map.put("index",UUID.randomUUID().toString().replaceAll("-",""));
        map.put("value",IMAGE_URL+filename);
        return new FebsResponse().success().data(map);

    }

}
