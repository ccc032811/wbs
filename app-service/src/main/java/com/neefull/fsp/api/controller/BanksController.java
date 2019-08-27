package com.neefull.fsp.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neefull.common.core.config.CardValidConfig;
import com.neefull.common.core.entity.FebsResponse;
import com.neefull.common.core.util.HttpUtils;
import com.neefull.fsp.api.annotation.AuthToken;
import com.neefull.fsp.api.entity.DictBanks;
import com.neefull.fsp.api.entity.UserCard;
import com.neefull.fsp.api.exception.BizException;
import com.neefull.fsp.api.mapper.DictBanksMapper;
import com.neefull.fsp.api.service.IDictBanksService;
import com.neefull.fsp.api.service.IUserCardService;
import com.neefull.fsp.api.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pei.wang
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/bank")
public class BanksController {

    @Autowired
    private IDictBanksService dictBanksService;
    @Value("cardBankApi")
    private String cardBankApi;
    @Autowired
    DictBanksMapper dictBanksMapper;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 根据卡号，校验卡类型
     *
     * @param cardNo
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/checkCardNo", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    // @AuthToken
    public String checkCardNo(@RequestBody JSONObject cardNo) throws BizException {

        Map<String, String> querys = new HashMap<>();
        querys.put("cardNo", cardNo.getString("cardNo"));
        querys.put("cardBinCheck", "true");
        JSONArray allBanks = null;
        try {
            HttpResponse response = HttpUtils.doGet("https://ccdcapi.alipay.com", "/validateAndCacheCardInfo.json", "GET", new HashMap<>(), querys);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                JSONObject object = JSONObject.parseObject(result);
                boolean validated = (boolean) object.get("validated");
                String cardType = (String) object.get("cardType");
                if (null != cardType && "CC".equals(cardType)) {
                    return new FebsResponse().fail().data("").message("不支持信用卡作为结算卡").toJson();
                }
                if (validated) {
                    String bank_abbr = (String) object.get("bank");
                    DictBanks dictBanks = dictBanksMapper.findBankName(bank_abbr);
                    if (null != dictBanks) {
                        JSONObject resultJson = new JSONObject();
                        resultJson.put("bankId", dictBanks.getBankId());
                        resultJson.put("bankName", dictBanks.getBankName());
                        return new FebsResponse().success().data(resultJson).message("").toJsonNoNull();
                    }
                }
            }
            //查询失败，则返回所有银行信息
            List<DictBanks> lst = dictBanksMapper.listAllBank();
            allBanks = new JSONArray();
            for (DictBanks dictBank : lst) {
                JSONObject resultJson = new JSONObject();
                resultJson.put("bankId", dictBank.getBankId());
                resultJson.put("bankName", dictBank.getBankName());
                allBanks.add(resultJson);
            }
            return new FebsResponse().success().data(allBanks).message("未查询到卡号所属银行").toJsonNoNull();
        } catch (Exception e) {
            return new FebsResponse().success().data(allBanks).message("未查询到卡号所属银行").toJsonNoNull();
        }
    }

    /**
     * 查询省市信息
     *
     * @return
     */
    @RequestMapping(value = "/queryProvinceAndCity", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String queryProvinceAndCity() {
        Object result = null;
        if (null == redisUtil.get("provinces")) {
            List<DictBanks> lst = dictBanksMapper.queryAllProvince();
            JSONArray jsonArray = new JSONArray();
            for (DictBanks province : lst) {
                JSONObject provinceJson = new JSONObject();
                provinceJson.put("provinceId", province.getProvinceId());
                provinceJson.put("province", province.getProvince());
                JSONArray cityArray = new JSONArray();
                List<DictBanks> citys = dictBanksMapper.queryAllCity(province.getProvinceId());
                for (DictBanks city : citys) {
                    JSONObject cityJson = new JSONObject();
                    cityJson.put("cityId", city.getCityId());
                    cityJson.put("city", city.getCity());
                    cityArray.add(cityJson);
                }
                provinceJson.put("citys", cityArray);
                jsonArray.add(provinceJson);
            }
            redisUtil.set("provinces", jsonArray);
            result = jsonArray;

        } else {
            result = redisUtil.get("provinces");
        }
        return new FebsResponse().success().data(result).message("").toJsonNoNull();

    }

    /**
     * 根据银行和城市，查询支行信息
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/querySubBranchs", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String querySubBranchs(@RequestBody JSONObject params) {

        List<DictBanks> lst = dictBanksMapper.querySubBranch((int) params.get("cityId"), (int) params.get("bankId"));
        JSONArray subs = new JSONArray();
        for (DictBanks subBranch : lst) {
            JSONObject cityJson = new JSONObject();
            cityJson.put("subBranchId", subBranch.getSubBranchId());
            cityJson.put("subBranch", subBranch.getSubBranchName());
            subs.add(cityJson);
        }
        return new FebsResponse().success().data(subs).message("").toJsonNoNull();

    }

    @Autowired
    CardValidConfig cardValidConfig;
    @Autowired
    IUserCardService userCardService;

    /**
     * 绑定用户结算卡
     *
     * @param userCard
     * @return
     */
    @RequestMapping(value = "/bindUserCard", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @AuthToken
    public String bindUserCard(@RequestBody UserCard userCard, HttpServletRequest httpRequest) {
        long userId = (long) httpRequest.getAttribute("userId");
        userCard.setUserId(userId);
        //根据卡号，姓名去校验卡和名称，是否一致
        String appCode = cardValidConfig.getAppCode();
        String host = cardValidConfig.getHost();
        String path = cardValidConfig.getPath();
        String method = cardValidConfig.getMethod();
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appCode);
        querys.put(cardValidConfig.getParaCName(), userCard.getRealName());
        querys.put(cardValidConfig.getParaCNo(), userCard.getCardNo());
        String code = "";
        String msg = "";
        try {
            JSONObject retJson = null;
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                retJson = JSONObject.parseObject(EntityUtils.toString(entity, "UTF-8"));
                JSONObject body = (JSONObject) retJson.get("showapi_res_body");
                code = body.getString("code");
                if (code.equals("0") && (userCardService.saveUserCard(userCard)) > 0) {
                    //校验通过，保存用户卡信息
                    msg = "绑卡成功";
                } else {
                    msg = body.getString("msg");
                }
            }
        } catch (Exception e) {
            new BizException("网络异常，请稍后重试");
        }
        return code.equals("0") ? new FebsResponse().success().message(msg).data("").toJson() : new FebsResponse().fail().message(msg).toJson();
    }


}
