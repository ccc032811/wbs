package com.neefull.fsp.web.sms.utils;

import com.neefull.fsp.web.sms.entity.Detail;
import com.neefull.fsp.web.sms.entity.Header;
import com.neefull.fsp.web.sms.entity.vo.DetailVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**解析XML获取DN对应的信息
 * @Author: chengchengchu
 * @Date: 2020/11/26  15:53
 */
public abstract class XmlUtils {

    public static Header resolverSapMessage(String sapMessage){

//        String soldToParty = getTagContent(sapMessage, "<SOLD_TO_PARTY>", "</SOLD_TO_PARTY>");
//        String shipToParty = getTagContent(sapMessage, "<SHIP_TO_PARTY>", "</SHIP_TO_PARTY>");
        Header header = new Header();
//        if(StringUtils.isNotEmpty(soldToParty)&&StringUtils.isNotEmpty(shipToParty)){

            String delivery = getTagContent(sapMessage, "<DELIVERY>", "</DELIVERY>");
            header.setDelivery(delivery);
            header.setSoldParty(getTagContent(sapMessage,"<SOLD_TO_PARTY>","</SOLD_TO_PARTY>"));
            header.setSoldPartyName(getTagContent(sapMessage,"<SOLD_TO_PARTY_NAME>","</SOLD_TO_PARTY_NAME>"));
            header.setShipParty(getTagContent(sapMessage,"<SHIP_TO_PARTY>","</SHIP_TO_PARTY>"));
            header.setShipPartyName(getTagContent(sapMessage,"<SHIP_TO_PARTY_NAME>","</SHIP_TO_PARTY_NAME>"));
            header.setAddress(getTagContent(sapMessage,"<SHIP_TO_ADDRESS>","</SHIP_TO_ADDRESS>"));
            header.setSalesOrder(getTagContent(sapMessage,"<SALES_ORDER>","</SALES_ORDER>"));
            header.setRocheDelivery(getTagContent(sapMessage,"<ROCHE_DELIVERY>","</ROCHE_DELIVERY>"));
            header.setRocheShipParty(getTagContent(sapMessage,"<ROCHE_SHIP_TO_PARTY>","</ROCHE_SHIP_TO_PARTY>"));
            header.setRocheShipPartyName(getTagContent(sapMessage,"<ROCHE_SHIP_TO_PARTY_NAME>","</ROCHE_SHIP_TO_PARTY_NAME>"));
            header.setRocheOrder(getTagContent(sapMessage, "<ROCHE_SALES_ORDER>", "</ROCHE_SALES_ORDER>"));
            header.setRocheCustomerOrder(getTagContent(sapMessage, "<ROCHE_CUSTOMER_ORDER>", "</ROCHE_CUSTOMER_ORDER>"));

//            String detailMessage = getTagContent(sapMessage, "<T_DELIVERY_ITEM>", "</T_DELIVERY_ITEM>");
//            String[] detailString = detailMessage.split("<item>");
//            List<String> detailStrList = new ArrayList<>();
//            for (int i=1; i<detailString.length;i++){
//                if(StringUtils.isNotEmpty(detailString[i])){
//                    String dom = detailString[i].split("</item>")[0];
//                    detailStrList.add(dom);
//                }
//            }
            //截取detail
            List<String> detailStrList = getDetailList(sapMessage);

            List<Detail> detailList = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(detailStrList)){
                for (String str : detailStrList) {
                    Detail detail = new Detail();
                    detail.setDelivery(delivery);
                    detail.setDeliveryItem(getTagContent(str, "<DELIVERY_ITEM>", "</DELIVERY_ITEM>"));
                    detail.setRocheDeliveryItem(getTagContent(str, "<ROCHE_DELIVERY_ITEM>", "</ROCHE_DELIVERY_ITEM>"));
                    detail.setRocheDeliveryItemCode(getTagContent(str, "<ROCHE_DELIVERY_ITEM_COUNT>", "</ROCHE_DELIVERY_ITEM_COUNT>"));
                    detail.setMaterial(getMaterial(getTagContent(str, "<MATERIAL>", "</MATERIAL>")));
                    detail.setRocheMaterial(getTagContent(str, "<ROCHE_MATERIAL>", "</ROCHE_MATERIAL>"));
                    detail.setMaterialDescription(getTagContent(str, "<MATERIAL_DESCRIPTION>", "</MATERIAL_DESCRIPTION>"));
                    detail.setMaterialDescriptionEn(getTagContent(str, "<MATERIAL_DESCRIPTION_EN>", "</MATERIAL_DESCRIPTION_EN>"));
                    detail.setRocheMaterialDescription(getTagContent(str, "<ROCHE_MATERIAL_DESCRIPTION>", "</ROCHE_MATERIAL_DESCRIPTION>"));
                    detail.setPlant(getTagContent(str, "<PLANT>", "</PLANT>"));
//                    detail.setBatch(getTagContent(str, "<BATCH>", "</BATCH>"));
                    detail.setRocheBatch("");
//                    detail.setSerialNumber(getTagContent(str, "<SERIAL_NUMBER>", "</SERIAL_NUMBER>"));
                    detail.setQuantity(getTagContent(str, "<QUANTITY>", "</QUANTITY>"));
                    detail.setUom(getTagContent(str, "<UOM>", "</UOM>"));
                    detail.setRocheUom(getTagContent(str, "<ROCHE_UOM>", "</ROCHE_UOM>"));
                    detail.setExpiryDate(getTagContent(str, "<EXPIRY_DATE>", "</EXPIRY_DATE>"));
                    detail.setIfBatchManagement(getTagContent(str, "<IF_BATCH_MANAGEMENT>", "</IF_BATCH_MANAGEMENT>"));
                    detail.setIfSerialNumberManagement(getTagContent(str, "<IF_SERIAL_NUMBER_MANAGEMENT>", "</IF_SERIAL_NUMBER_MANAGEMENT>"));
                    detail.setIfExpiryDateManagement(getTagContent(str, "<IF_EXPIRY_DATE_MANAGEMENT>", "</IF_EXPIRY_DATE_MANAGEMENT>"));
                    detail.setSaveMode(getTagContent(str, "<SAVE_MODE>", "</SAVE_MODE>"));
                    detail.setSaveModeDescription(getTagContent(str, "<SAVE_MODE_DESCRIPTION>", "</SAVE_MODE_DESCRIPTION>"));

                    boolean isContain = false;
                    //将detail 集合中相同物料，把他们的数量相加合并
                    if(CollectionUtils.isNotEmpty(detailList)){
                        for (Detail deta : detailList) {
                            if(StringUtils.isNotEmpty(detail.getBatch())){
                                if (detail.getMaterial().equals(deta.getMaterial()) && detail.getRocheBatch().equals(deta.getRocheBatch())) {
                                    String count = getCount(deta.getQuantity(), detail.getQuantity());
                                    deta.setQuantity(count);
                                    if (!deta.getBatch().equals(detail.getBatch())) {
                                        deta.setBatch(deta.getBatch() + "|" + detail.getBatch());
                                    }
                                    isContain = true;
                                }
                            }else {
                                if(detail.getMaterial().equals(deta.getMaterial())){
                                    //数量相加
                                    String count = getCount(deta.getQuantity(), detail.getQuantity());
                                    deta.setQuantity(count);
                                    isContain = true;
                                }
                            }

                        }
                    }
                    if(!isContain){
                        detailList.add(detail);
                    }
                }
                header.setDetailList(detailList);
                header.setPlant(getTagContent(sapMessage, "<PLANT>", "</PLANT>"));
            }
//        }
        return header;
    }



    private static String getCount(String res1,String res2){
        BigDecimal detaDec = new BigDecimal(res1);
        BigDecimal detailDec = new BigDecimal(res2);
        detaDec = detaDec.add(detailDec);
        return String.valueOf(detaDec);
    }


    private static List<String> getDetailList(String msg){

        String detailMessage = getTagContent(msg, "<T_DELIVERY_ITEM>", "</T_DELIVERY_ITEM>");
        String[] detailString = detailMessage.split("<item>");
        List<String> detailStrList = new ArrayList<>();
        for (int i=1; i<detailString.length;i++){
            if(StringUtils.isNotEmpty(detailString[i])){
                String dom = detailString[i].split("</item>")[0];
                detailStrList.add(dom);
            }
        }
        return detailStrList;
    }



    public static List<DetailVo> resolverDetail(String sapMessage){

//        String detailMessage = getTagContent(sapMessage, "<T_DELIVERY_ITEM>", "</T_DELIVERY_ITEM>");
//        String[] detailString = detailMessage.split("<item>");
//        List<String> detailStrList = new ArrayList<>();
//        for (int i=1; i<detailString.length;i++){
//            if(StringUtils.isNotEmpty(detailString[i])){
//                String dom = detailString[i].split("</item>")[0];
//                detailStrList.add(dom);
//            }
//        }

        List<String> detailStrList = getDetailList(sapMessage);

        List<DetailVo> detailVoList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(detailStrList)){
            for (String str : detailStrList) {
                DetailVo detailVo = new DetailVo();

                detailVo.setMatCode(getMaterial(getTagContent(str, "<MATERIAL>", "</MATERIAL>")));
                detailVo.setMatName(getTagContent(str, "<MATERIAL_DESCRIPTION>", "</MATERIAL_DESCRIPTION>"));
                detailVo.setBatch("");
//                detailVo.setBatch(getTagContent(str, "<ROCHE_BATCH>", "</ROCHE_BATCH>"));
//                detailVo.setSerialNumber(getTagContent(str, "<SERIAL_NUMBER>", "</SERIAL_NUMBER>"));
                detailVo.setQuantity(getTagContent(str, "<QUANTITY>", "</QUANTITY>"));
                detailVo.setUnit(getTagContent(str, "<UOM>", "</UOM>"));
                detailVo.setExpiryDate(getTagContent(str, "<EXPIRY_DATE>", "</EXPIRY_DATE>"));

                boolean isContain = false;
                if(CollectionUtils.isNotEmpty(detailVoList)){

                    for (DetailVo deta : detailVoList) {
                        if(StringUtils.isNotEmpty(detailVo.getBatch())){
                            if (detailVo.getMatCode().equals(deta.getMatCode()) && detailVo.getBatch().equals(deta.getBatch())) {
                                String count = getCount(deta.getQuantity(), detailVo.getQuantity());
                                deta.setQuantity(count);
                                if (!deta.getBatch().equals(detailVo.getBatch())) {
                                    deta.setBatch(deta.getBatch() + "|" + detailVo.getBatch());
                                }
                                isContain = true;
                            }
                        }else {
                            if(detailVo.getMatCode().equals(deta.getMatCode())){
                                String count = getCount(deta.getQuantity(), detailVo.getQuantity());
                                deta.setQuantity(count);
                                isContain = true;
                            }
                        }
                    }


//                    for (DetailVo deta : detailVoList) {
//                        if(detailVo.getMatCode().equals(deta.getMatCode())&&detailVo.getBatch().equals(deta.getBatch())){
//                            BigDecimal detaDec = new BigDecimal(deta.getQuantity());
//                            BigDecimal detailDec = new BigDecimal(detailVo.getQuantity());
//                            detaDec = detaDec.add(detailDec);
//                            deta.setQuantity(String.valueOf(detaDec));
//                            isContain = true;
//                        }
//                    }

                }
                if(!isContain){
                    detailVoList.add(detailVo);
                }
            }
        }
        return detailVoList;
    }


    public static String getTagContent(String xmlStr, String beginTag, String endTag) {
        if(xmlStr.contains(beginTag)) {
            return xmlStr.split(beginTag)[1].split(endTag)[0];
        }else {
            return "";
        }
    }

    private static String getMaterial(String val){
        int newVal = Integer.parseInt(val);
        return String.valueOf(newVal);
    }



}
