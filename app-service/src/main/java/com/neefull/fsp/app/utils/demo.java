package com.neefull.fsp.app.utils;

import com.alibaba.fastjson.JSONObject;
import com.neefull.fsp.app.entity.UserResume;

public class demo {

    public static void main(String[] args) {
        UserResume userResume = new UserResume();
        // JSONObject jsonObject = (JSONObject) JSONObject.toJSON(userResume);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(JSONObject.toJSON(userResume)));
        System.out.println(jsonObject.keySet().size());
        //System.out.println(JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));
    }
}
