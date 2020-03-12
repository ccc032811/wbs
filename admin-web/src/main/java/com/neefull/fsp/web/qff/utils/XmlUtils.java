package com.neefull.fsp.web.qff.utils;

import org.dom4j.DocumentException;
//import ws.sap.gsp.ZCHN_CHP_FM_PRNLIST;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlUtils {

    public static String beanToXml(Object obj, Class<?> load) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(load);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        return writer.toString();
    }

    public static Object xmlToBean(String xmlBody, Class<?> load) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(load);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xmlBody);
        Object object = unmarshaller.unmarshal(reader);
        return object;
    }

    /**
     * 获取指定xml串中标签之间的内容
     */
    public static String getTagContent(String xmlStr, String beginTag, String endTag) throws DocumentException {


        if(xmlStr.contains(beginTag)) {
            return xmlStr.split(beginTag)[1].split(endTag)[0];
        }else {
            return "";
        }
    }

}
