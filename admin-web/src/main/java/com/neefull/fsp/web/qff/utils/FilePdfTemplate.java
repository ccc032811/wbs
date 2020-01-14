package com.neefull.fsp.web.qff.utils;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: chengchengchu
 * @Date: 2019/12/11  14:51
 */


@Component
public class FilePdfTemplate {

     /**生成pdf
     * @param map   数据信息
     * @param templatePath  模板地址
     * @param downLoadPath  生成pdf文件夹地址
     * @param newPDFPath    pdf的url
     * @return
     */


    public String createPdf(Map<String,String> map,String templatePath,String downLoadPath,String newPDFPath ){
        File dir = new File(downLoadPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        //查询是否存在
        File file = new File(newPDFPath);
        if(file.exists()){
            file.delete();
        }
        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            out = new FileOutputStream(newPDFPath);
            reader = new PdfReader(templatePath);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next().toString();
                if(map.get(name)==null){
                    form.setField(name, "");
                }else {
                    form.setField(name, map.get(name));
                }
            }
            stamper.setFormFlattening(true);
            stamper.close();
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return newPDFPath;
    }


}
