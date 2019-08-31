package com.neefull.fsp.app.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 自定义公共字段填充处理器
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入填充
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 获取公共字段的值
        Object fieldValue = getFieldValByName("createTime", metaObject);
        // 判断是否为空,如果为空则进行填充
        if (fieldValue == null) {
            setFieldValByName("createTime", new Timestamp(new Date().getTime()), metaObject);
        }
    }

    /**
     * 修改填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Object fieldValue = getFieldValByName("modifyTime", metaObject);
        if (fieldValue == null) {
            setFieldValByName("modifyTime", new Timestamp(new Date().getTime()), metaObject);
        }
    }
}
