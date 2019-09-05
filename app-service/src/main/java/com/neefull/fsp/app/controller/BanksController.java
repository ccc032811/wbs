package com.neefull.fsp.app.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neefull.fsp.app.annotation.AuthToken;
import com.neefull.fsp.app.entity.DictBanks;
import com.neefull.fsp.app.exception.BizException;
import com.neefull.fsp.app.mapper.DictBanksMapper;
import com.neefull.fsp.app.service.IDictBanksService;
import com.neefull.fsp.app.utils.RedisUtil;
import com.neefull.fsp.common.entity.FebsResponse;
import com.neefull.fsp.common.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @AuthToken
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
            return new FebsResponse().fail().data(allBanks).message("未查询到卡号所属银行").toJsonNoNull();
        } catch (Exception e) {
            return new FebsResponse().fail().data(allBanks).message("未查询到卡号所属银行").toJsonNoNull();
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

}
