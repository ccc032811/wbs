package com.neefull.fsp.web.qff.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.qff.aspect.Qff;
import com.neefull.fsp.web.qff.config.ProcessInstanceProperties;
import com.neefull.fsp.web.qff.config.SendMailProperties;
import com.neefull.fsp.web.qff.entity.Recent;
import com.neefull.fsp.web.qff.entity.RecentResolver;
import com.neefull.fsp.web.qff.service.IDateImageService;
import com.neefull.fsp.web.qff.service.IFileService;
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
    private IFileService fileService;
    @Autowired
    private IDateImageService dateImageService;
    @Autowired
    private IProcessService processService;


    /**查询需要完成任务
     * @return
     */
    @GetMapping("/findTask")
    public FebsResponse findTask() throws FebsException {
        try {
            User user = getCurrentUser();
            Integer count = processService.findTask(user.getUsername());
            return new FebsResponse().success().data(count);
        } catch (Exception e) {
            String message = "查询需要完成的任务数失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**查询所有图片
     * @param dataId
     * @param relevance
     * @return
     */
    @GetMapping("/findImages/{dataId}/{relevance}")
    public FebsResponse findImageByIdAndForm(@PathVariable Integer dataId ,@PathVariable String relevance) throws FebsException {

        try {
            List<String> list = dateImageService.findImageByIdAndForm(dataId,relevance);
            return new FebsResponse().success().data(list);
        } catch (Exception e) {
            String message = "查询所有图片失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**删除图片
     * @param url
     * @return
     */
    @GetMapping("/deleteImage/{url}")
    public FebsResponse deleteImage(@PathVariable String url) throws FebsException {
        try {
            dateImageService.deleteImage(url);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除图片失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**上传图片
     * @param file
     * @return
     * @throws FebsException
     */
    @PostMapping("/uploadImage")
    public FebsResponse uploadImage(@RequestParam("file") MultipartFile file) throws FebsException {

        try {
            Map<String,String> map = fileService.uploadImage(file);
            return new FebsResponse().success().data(map);
        } catch (Exception e) {
            String message = "上传图片失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }


    /**解析exexl
     * @param file
     * @return
     * @throws FebsException
     */
    @Qff("解析近效期QFF")
    @PostMapping("/resolver")
    @RequiresPermissions("recent:import")
    public FebsResponse resolverExcel(@RequestParam("file") MultipartFile file) throws FebsException {

        fileService.resolverExcel(file,getCurrentUser());
        return new FebsResponse().success();

    }




}
