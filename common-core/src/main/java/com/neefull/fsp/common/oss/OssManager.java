package com.neefull.fsp.common.oss;

import com.neefull.fsp.common.config.QiniuConfig;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.StringUtils;

import java.io.UnsupportedEncodingException;

public class OssManager {

    private final String domainOfBucket = "http://pwqzsgxmt.bkt.clouddn.com";
    private final String actionUrl = "http://upload.qiniup.com/";

    public String getUpToken(QiniuConfig qiniuConfig) {
        String accessKey = qiniuConfig.getAccessKey();
        String secretKey = qiniuConfig.getSecretKey();
        String bucket = qiniuConfig.getBucket();
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        putPolicy.put("insertOnly", 0);
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        return upToken;

    }

    public String getDownUrl(QiniuConfig qiniuConfig, String fileName) throws UnsupportedEncodingException {
        if (StringUtils.isNullOrEmpty(fileName)) {
            return "";
        }
        //String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        String publicUrl = String.format("%s/%s", domainOfBucket, fileName);
        String accessKey = qiniuConfig.getAccessKey();
        String secretKey = qiniuConfig.getSecretKey();
        Auth auth = Auth.create(accessKey, secretKey);
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        return finalUrl;
    }

}
