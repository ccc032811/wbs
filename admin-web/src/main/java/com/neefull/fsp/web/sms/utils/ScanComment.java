package com.neefull.fsp.web.sms.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chengchengchu
 * @Date: 2020/12/1  11:21
 */

public abstract class ScanComment {

    public final static String STATUS_ONE = "1";  // 1 已扫描

    public final static String STATUS_TWO = "2";  // 2   审核未通过

    public final static String STATUS_THREE = "3";  //3 审核已通过

    public final static String STATUS_FOUR = "4";  //已同步

    public final static String ERRORMSG = "0000";

    private final static String PLANT = "plant";
    private final static String PLANTS = "plants";
    private final static String NOTHING = "无";


    public static Object containPlant(Object object){
        Map<String,Object> map = new HashMap<>();

        try {
            Class<?> aClass = object.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();

            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                Object val =  declaredField.get(object);
                map.put(declaredField.getName(),val);
            }
            String singlePlant = (String) map.get(PLANT);
            List<String> plantList = (List<String>) map.get(PLANTS);
            if(StringUtils.isNotEmpty(singlePlant)){
                String str = String.join(",", plantList);
                if(!str.contains(singlePlant)){
                    Field plant = null;
                    try {
                        plant = aClass.getDeclaredField(PLANT);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    plant.setAccessible(true);
                    plant.set(object,NOTHING);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

}
