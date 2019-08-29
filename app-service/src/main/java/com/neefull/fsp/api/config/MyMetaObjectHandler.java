package com.neefull.fsp.api.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

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
        Object fieldValue = getFieldValByName("create_time", metaObject);
        // 判断是否为空,如果为空则进行填充
        if (fieldValue == null) {
            setFieldValByName("create_time", new Date(), metaObject);
        }
    }

    /**
     * 修改填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Object fieldValue = getFieldValByName("modify_time", metaObject);
        if (fieldValue == null) {
            setFieldValByName("modify_time", new Date(), metaObject);
        }
    }
}
