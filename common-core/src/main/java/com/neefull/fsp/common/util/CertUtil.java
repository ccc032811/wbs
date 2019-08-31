package com.neefull.fsp.common.util;

import com.neefull.fsp.common.config.CardValidConfig;
import com.neefull.fsp.common.entity.BankCard;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 实名认证相关工具类
 */
@Component
public class CertUtil {
    public Map<String, String> userCardCert(CardValidConfig cardValidConfig, BankCard bankCard) {
        /*String appCode = cardValidConfig.getAppCode();
        String host = cardValidConfig.getHost();
        String path = cardValidConfig.getPath();
        String method = cardValidConfig.getMethod();
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appCode);
        querys.put(cardValidConfig.getRealName(), bankCard.getRealName());
        querys.put(cardValidConfig.getCardNo(), bankCard.getCardNo());
        querys.put(cardValidConfig.getCertNo(), bankCard.getCertNo());
        querys.put(cardValidConfig.getCertType(), "01");
        if (cardValidConfig.getPath().contains("bank4")) {
            querys.put(cardValidConfig.getPhoneNum(), "01");
        }
        String code = "";
        String msg = "";
        JSONObject retJson = null;
        HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
        HttpEntity entity = response.getEntity();*/
        Map<String, String> resultMap = new HashMap();
        /*if (null != entity) {
            retJson = JSONObject.parseObject(EntityUtils.toString(entity, "UTF-8"));
            JSONObject body = (JSONObject) retJson.get("showapi_res_body");
            code = body.getString("code");
            msg = body.getString("msg");
            resultMap.put("code",code);
            resultMap.put("msg",msg);
        }*/
        resultMap.put("code", "1");
        resultMap.put("msg", "失败");
        return resultMap;
    }

}
