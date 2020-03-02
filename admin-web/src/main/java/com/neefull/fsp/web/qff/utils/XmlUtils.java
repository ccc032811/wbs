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
        }else
        {
            return "";
        }
    }

    public static void main(String args[]) throws JAXBException, DocumentException, IOException {
        /*ZCHN_CHP_FM_PRNLIST zchn_chp_fm_prnlist = new ZCHN_CHP_FM_PRNLIST();
        List<ZCHN_CHP_ST_PERMISSION> params = new ArrayList<>();
        List<ZCHN_CHP_ST_PRNLIST_RETURN> returns = new ArrayList<>();
        ZCHN_CHP_ST_PERMISSION zchn_chp_st_permission1 = new ZCHN_CHP_ST_PERMISSION();
        zchn_chp_st_permission1.setCOMPANY_CODE("6270");
        ZCHN_CHP_ST_PERMISSION zchn_chp_st_permission2 = new ZCHN_CHP_ST_PERMISSION();
        zchn_chp_st_permission2.setCOMPANY_CODE("6280");
        params.add(zchn_chp_st_permission1);
        params.add(zchn_chp_st_permission2);

        ZCHN_CHP_ST_PRNLIST_RETURN zchn_chp_st_prnlist_return = new ZCHN_CHP_ST_PRNLIST_RETURN();
        zchn_chp_st_prnlist_return.setPRINCIPAL_CODE("");
        zchn_chp_st_prnlist_return.setPRINCIPAL_NAME("");
        returns.add(zchn_chp_st_prnlist_return);
        zchn_chp_fm_prnlist.setPermissions(params);
        zchn_chp_fm_prnlist.setReturns(returns);
        String str = beanToXml(zchn_chp_fm_prnlist, ZCHN_CHP_FM_PRNLIST.class);

        String requestText = str.split("<urn>")[1].split("</urn>")[0];

        System.out.println(str);*/


        String xml = "<urn><OT_RETURN>"+
                " <item>\n" +
                "               <PRINCIPAL_CODE>110924</PRINCIPAL_CODE>\n" +
                "               <PRINCIPAL_NAME>诺迈西(上海)医药科技有限公司</PRINCIPAL_NAME>\n" +
                "            </item>\n" +
                "            <item>\n" +
                "               <PRINCIPAL_CODE>110667</PRINCIPAL_CODE>\n" +
                "               <PRINCIPAL_NAME>中国癌症基金会</PRINCIPAL_NAME>\n" +
                "            </item>\n" +
                "         </OT_RETURN></urn>";
//        ZCHN_CHP_FM_PRNLIST zc = (ZCHN_CHP_FM_PRNLIST) xmlToBean(xml,ZCHN_CHP_FM_PRNLIST.class);
    }
}
