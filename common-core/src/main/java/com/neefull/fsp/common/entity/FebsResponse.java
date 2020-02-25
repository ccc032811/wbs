package com.neefull.fsp.common.entity;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author pei.wang
 */
public class FebsResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public FebsResponse code(HttpStatus status) {
        this.put("code", status.value());
        return this;
    }

    public FebsResponse message(String message) {
        this.put("msg", message);
        return this;
    }

    public FebsResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    public FebsResponse success() {
        this.code(HttpStatus.OK);
        this.message("");
        return this;
    }

    public FebsResponse fail() {
        this.code(HttpStatus.BAD_REQUEST);
        this.data(null);
        return this;
    }

    public FebsResponse error() {
        this.code(HttpStatus.INTERNAL_SERVER_ERROR);
        this.data(null);
        return this;
    }

    @Override
    public FebsResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String toJson() {
        return JSONObject.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:SS", SerializerFeature.WriteMapNullValue);
    }

    public String toJsonNoNull() {
        return JSONObject.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:SS");
    }
}
