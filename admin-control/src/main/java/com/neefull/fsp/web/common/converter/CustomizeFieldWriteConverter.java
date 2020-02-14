package com.neefull.fsp.web.common.converter;

import com.wuwenze.poi.convert.WriteConverter;
import com.wuwenze.poi.exception.ExcelKitWriteConverterException;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: fsp
 * @author: xi.chen
 * @create: 2019-09-27 15:35
 **/
@Slf4j
public class CustomizeFieldWriteConverter implements WriteConverter {
    @Override
    public String convert(Object o) throws ExcelKitWriteConverterException {
        return ("s-"+o);
    }
}