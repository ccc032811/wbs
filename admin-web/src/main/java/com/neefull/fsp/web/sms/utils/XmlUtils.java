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

        Header header = new Header();
        String headerMessage = getTagContent(sapMessage, "<E_DELIVERY_HEAD>", "</E_DELIVERY_HEAD>");
        String delivery = getTagContent(headerMessage, "<DELIVERY>", "</DELIVERY>");
        header.setDelivery(delivery);
        header.setSoldParty(getTagContent(headerMessage,"<SOLD_TO_PARTY>","</SOLD_TO_PARTY>"));
        header.setSoldPartyName(getTagContent(headerMessage,"<SOLD_TO_PARTY_NAME>","</SOLD_TO_PARTY_NAME>"));
        header.setShipParty(getTagContent(headerMessage,"<SHIP_TO_PARTY>","</SHIP_TO_PARTY>"));
        header.setShipPartyName(getTagContent(headerMessage,"<SHIP_TO_PARTY_NAME>","</SHIP_TO_PARTY_NAME>"));
        header.setAddress(getTagContent(headerMessage,"<SHIP_TO_ADDRESS>","</SHIP_TO_ADDRESS>"));
        header.setSalesOrder(getTagContent(headerMessage,"<SALES_ORDER>","</SALES_ORDER>"));
        header.setRocheDelivery(getTagContent(headerMessage,"<ROCHE_DELIVERY>","</ROCHE_DELIVERY>"));
        header.setRocheShipParty(getTagContent(headerMessage,"<ROCHE_SHIP_TO_PARTY>","</ROCHE_SHIP_TO_PARTY>"));
        header.setRocheShipPartyName(getTagContent(headerMessage,"<ROCHE_SHIP_TO_PARTY_NAME>","</ROCHE_SHIP_TO_PARTY_NAME>"));
        header.setRocheOrder(getTagContent(sapMessage, "<ROCHE_SALES_ORDER>", "</ROCHE_SALES_ORDER>"));
        header.setRocheCustomerOrder(getTagContent(sapMessage, "<ROCHE_CUSTOMER_ORDER>", "</ROCHE_CUSTOMER_ORDER>"));

        String detailMessage = getTagContent(sapMessage, "<T_DELIVERY_ITEM>", "</T_DELIVERY_ITEM>");
        String[] detailString = detailMessage.split("<item>");
        List<String> detailStrList = new ArrayList<>();
        for (int i=1; i<detailString.length;i++){
            if(StringUtils.isNotEmpty(detailString[i])){
                String dom = detailString[i].split("</item>")[0];
                detailStrList.add(dom);
            }
        }

        List<Detail> detailList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(detailStrList)){
            for (String str : detailStrList) {
                Detail detail = new Detail();
                detail.setDelivery(delivery);
                detail.setDeliveryItem(getTagContent(str, "<DELIVERY_ITEM>", "</DELIVERY_ITEM>"));
                detail.setRocheDeliveryItem(getTagContent(str, "<ROCHE_DELIVERY_ITEM>", "</ROCHE_DELIVERY_ITEM>"));
                detail.setRocheDeliveryItemCode(getTagContent(str, "<ROCHE_DELIVERY_ITEM_COUNT>", "</ROCHE_DELIVERY_ITEM_COUNT>"));
                detail.setMaterial(getTagContent(str, "<MATERIAL>", "</MATERIAL>"));
                detail.setRocheMaterial(getTagContent(str, "<ROCHE_MATERIAL>", "</ROCHE_MATERIAL>"));
                detail.setMaterialDescription(getTagContent(str, "<MATERIAL_DESCRIPTION>", "</MATERIAL_DESCRIPTION>"));
                detail.setMaterialDescriptionEn(getTagContent(str, "<MATERIAL_DESCRIPTION_EN>", "</MATERIAL_DESCRIPTION_EN>"));
                detail.setRocheMaterialDescription(getTagContent(str, "<ROCHE_MATERIAL_DESCRIPTION>", "</ROCHE_MATERIAL_DESCRIPTION>"));
                detail.setPlant(getTagContent(str, "<PLANT>", "</PLANT>"));
                detail.setBatch(getTagContent(str, "<BATCH>", "</BATCH>"));
                detail.setRocheBatch(getTagContent(str, "<ROCHE_BATCH>", "</ROCHE_BATCH>"));
                detail.setSerialNumber(getTagContent(str, "<SERIAL_NUMBER>", "</SERIAL_NUMBER>"));
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
                if(CollectionUtils.isNotEmpty(detailList)){
                    for (Detail deta : detailList) {
                        if(detail.getMaterial().equals(deta.getMaterial())&&detail.getBatch().equals(deta.getBatch())){
                            BigDecimal detaDec = new BigDecimal(deta.getQuantity());
                            BigDecimal detailDec = new BigDecimal(detail.getQuantity());
                            detaDec = detaDec.add(detailDec);
                            deta.setQuantity(String.valueOf(detaDec));
                            isContain = true;
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
        return header;
    }



    public static List<DetailVo> resolverDetail(String sapMessage){

        String detailMessage = getTagContent(sapMessage, "<T_DELIVERY_ITEM>", "</T_DELIVERY_ITEM>");
        String[] detailString = detailMessage.split("<item>");
        List<String> detailStrList = new ArrayList<>();
        for (int i=1; i<detailString.length;i++){
            if(StringUtils.isNotEmpty(detailString[i])){
                String dom = detailString[i].split("</item>")[0];
                detailStrList.add(dom);
            }
        }

        List<DetailVo> detailVoList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(detailStrList)){
            for (String str : detailStrList) {
                DetailVo detailVo = new DetailVo();

                detailVo.setMatCode(getTagContent(str, "<MATERIAL>", "</MATERIAL>"));
                detailVo.setMatName(getTagContent(str, "<MATERIAL_DESCRIPTION>", "</MATERIAL_DESCRIPTION>"));
                detailVo.setBatch(getTagContent(str, "<BATCH>", "</BATCH>"));
                detailVo.setSerialNumber(getTagContent(str, "<SERIAL_NUMBER>", "</SERIAL_NUMBER>"));
                detailVo.setQuantity(getTagContent(str, "<QUANTITY>", "</QUANTITY>"));
                detailVo.setUnit(getTagContent(str, "<UOM>", "</UOM>"));
                detailVo.setExpiryDate(getTagContent(str, "<EXPIRY_DATE>", "</EXPIRY_DATE>"));

                boolean isContain = false;
                if(CollectionUtils.isNotEmpty(detailVoList)){
                    for (DetailVo deta : detailVoList) {
                        if(detailVo.getMatCode().equals(deta.getMatCode())&&detailVo.getBatch().equals(deta.getBatch())){
                            BigDecimal detaDec = new BigDecimal(deta.getQuantity());
                            BigDecimal detailDec = new BigDecimal(detailVo.getQuantity());
                            detaDec = detaDec.add(detailDec);
                            deta.setQuantity(String.valueOf(detaDec));
                            isContain = true;
                        }
                    }
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

}
