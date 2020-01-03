package com.neefull.fsp.web.qff.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.qff.entity.DateImage;
import com.neefull.fsp.web.qff.entity.ImageQuery;
import com.neefull.fsp.web.qff.mapper.DateImageMapper;
import com.neefull.fsp.web.qff.service.IDateImageService;
import com.neefull.fsp.web.system.entity.User;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2019/12/26  18:34
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DateImageServiceImpl extends ServiceImpl<DateImageMapper, DateImage> implements IDateImageService {

    private static final String IMAGE_DIR ="D:\\JavaSoft\\nginx-1.12.2\\html\\";
    private static final String IMAGE_URL ="http://127.0.0.1:10086/";

    @Autowired
    private DateImageMapper dateImageMapper;

    @Override
    public String queryImage(Integer dataId, String vest,String relevance) {
        DateImage date = dateImageMapper.queryImage(dataId, vest, relevance);
        if(date ==null){
            return null;
        }else {
            return date.getImage();
        }
    }

    @Override
    public List<String> findImageByIdAndForm(Integer dataId, String relevance) {
        List<String> imageList = new ArrayList<>();
        List<DateImage> list = dateImageMapper.findImageByIdAndForm(dataId,relevance);
        for (DateImage dateImage : list) {
            String[] images = dateImage.getImage().split(StringPool.COMMA);
            if(images.length!=0){
                for (String image : images) {
                    imageList.add(IMAGE_URL+image);
                }
            }
        }
        return imageList;
    }

    @Override
    public void insertDateImage(Integer dataId, String vest, String relevance, String url) {
        DateImage dateImage = new DateImage();
        dateImage.setImage(url);
        dateImage.setVest(vest);
        dateImage.setDataId(dataId);
        dateImage.setRelevance(relevance);
        //设置来源
        dateImage.setSource(2);
        dateImageMapper.insert(dateImage);
    }

    @Override
    public void updateDateImage(Integer dataId, String vest, String relevance, String image) {
        DateImage dateImage = new DateImage();
        UpdateWrapper<DateImage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("vest",vest)
                .eq("data_id",dataId)
                .eq("relevance",relevance)
                .eq("source",2);
        dateImage.setImage(image);
        dateImageMapper.update(dateImage,updateWrapper);
    }

    @Override
    public void deleteImage(String url) {
        File file = new File(IMAGE_DIR+url);
        if(file.exists()){
            file.delete();
        }
    }

}
