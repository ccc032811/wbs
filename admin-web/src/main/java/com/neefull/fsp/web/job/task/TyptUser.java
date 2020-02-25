package com.neefull.fsp.web.job.task;

import com.neefull.fsp.web.system.entity.User;
import com.neefull.fsp.web.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: chengchengchu
 * @Date: 2020/2/18  20:24
 */

@Slf4j
@Component
public class TyptUser {

    @Autowired
    private IUserService userService;

    public void getTyptUser(){
        List<User> allUser = userService.getAllUser();
        for (User typtUser : allUser) {
            User user = userService.getUserByName(typtUser.getUsername());
            if(user==null){
                typtUser.setSex("2");
                userService.insertUser(typtUser);
            }else {
                userService.updateById(typtUser);
            }
        }
    }
}
