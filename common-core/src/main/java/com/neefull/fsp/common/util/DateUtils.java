package com.neefull.fsp.common.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtils {

    /**
     * 按照固定格式获得当前时间
     *
     * @Param format 格式化的  yyyyMMddHHmmss
     */

    public static String getDateWithFormat(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 获取前几天的日期
     *
     * @param formart 日期格式
     * @param days    前几天
     * @return
     */

    public String getBeforeDate(String formart, int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -days);
        Date d = cal.getTime();
        SimpleDateFormat sp = new SimpleDateFormat(formart);
        return sp.format(d);
    }
}
