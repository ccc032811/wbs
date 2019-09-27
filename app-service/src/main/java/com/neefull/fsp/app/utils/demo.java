package com.neefull.fsp.app.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.neefull.fsp.app.entity.TaskAnnex;

public class demo {

    public static void main(String[] args) {
        //UserResume userResume = new UserResume();
        TaskAnnex taskAnnex = new TaskAnnex();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(taskAnnex);
       // JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(JSONObject.toJSON(userResume)));
       // System.out.println(jsonObject.keySet().size());*/
        System.out.println(JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));
    }
}
