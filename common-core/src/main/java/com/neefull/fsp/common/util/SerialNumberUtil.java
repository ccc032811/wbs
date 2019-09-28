package com.neefull.fsp.common.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 流水号生成工具
 */
@Component
public abstract class SerialNumberUtil {
    /**
     * 生成唯一流水号
     *
     * @param prefix
     * @return
     */
    public static String getNextSerialNumber(String prefix, int length) {
        //获取当前时间，到毫秒级别,最大限度避免出现重复
        String currentMi = new SimpleDateFormat("YYYYMMddHHmmss").format(new Date());
        //生成随机数
        String randomNum = getRandom(length);
        return (null == prefix ? "游客" + currentMi + randomNum : prefix + currentMi + randomNum);
    }

    /**
     * 生成固定位数的随机数
     *
     * @param length 长度
     * @return 固定位数字符串
     */
    private static String getRandom(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }

    /**
     * 顺序编号生成器
     *
     * @param equipmentType
     * @param equipmentNo
     * @return
     */
    public synchronized static String getNewNo(String equipmentType, String equipmentNo) {

        String newEquipmentNo = equipmentNo;

        if (equipmentNo != null && !equipmentNo.isEmpty()) {
            long newEquipment = Long.parseLong(equipmentNo) + 1;
            newEquipmentNo = String.format(equipmentType + "%08d", newEquipment);
        }

        return newEquipmentNo;
    }

}
