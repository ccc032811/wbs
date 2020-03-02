package com.neefull.fsp.web.qff.utils;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * sap webservice的工具类，目的在于实现连接sapwebservice
 */
public abstract class SapWsUtils {
    private static final String encoding = "UTF-8";

    /**
     * 调用sap webservie
     *
     * @param invoke_ws_url 调用地址
     * @param soapMsg       soap消息
     * @param userName      用户名
     * @param password      密码
     * @return 返回的字符串
     * @throws Exception
     */
    public static StringBuffer callWebService(String invoke_ws_url, String soapMsg, String userName, String password, String contentType) throws Exception {
        // 开启HTTP连接ַ
        InputStreamReader isr = null;
        BufferedReader inReader = null;
        StringBuffer result = null;
        OutputStream outObject = null;
        String input = userName + ":" + password;
        BASE64Encoder base = new BASE64Encoder();
        String encodedPassword = base.encode(input.getBytes(encoding));

        try {
            URL url = new URL(invoke_ws_url);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestProperty("Authorization", "Basic " + encodedPassword);
            // 设置HTTP请求相关信息
            httpConn.setRequestProperty("Content-Length",
                    String.valueOf(soapMsg.getBytes().length));
            httpConn.setRequestProperty("Content-Type", contentType);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);

            // 进行HTTP请求
            outObject = httpConn.getOutputStream();
            outObject.write(soapMsg.getBytes());
            System.out.println(httpConn.getResponseMessage());
            // 获取HTTP响应数据
            isr = new InputStreamReader(
                    httpConn.getInputStream(), encoding);
            inReader = new BufferedReader(isr);
            result = new StringBuffer();
            String inputLine;
            while ((inputLine = inReader.readLine()) != null) {
                result.append(inputLine);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            // 关闭输入流
            if (inReader != null) {
                inReader.close();
            }
            if (isr != null) {
                isr.close();
            }
            // 关闭输出流
            if (outObject != null) {
                outObject.close();
            }
        }
        return result;
    }

    /**
     * 根据消息模板组装消息
     *
     * @param methodName 方法名称
     * @return
     */
    public String getSoapMsg(String methodName, Map<String, String> params) {
        String fileName = this.getClass().getResource("/soap/" + methodName + ".xml").getPath();
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        FileInputStream in = null;
        String sendMsg = null;
        try {
            in = new FileInputStream(file);
            in.read(filecontent);
            sendMsg = new String(filecontent, encoding);
            //解析出来占位符信息，并按照map里面参数进行匹配设置
            if (params.size() > 0) {
                for (String key : params.keySet()) {
                    sendMsg = sendMsg.replace("${" + key + "}", params.get(key));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sendMsg;
    }


    public static void main(String[] args) throws Exception {
/*        Map params = new HashMap();
        params.put("COMPANY_CODE", "6270");
        params.put("PLANT_CODE", "");
        params.put("PRINCIPAL_CODE", "101948");
        String INVOICE_WS_URL = "http://eccappqas.sphkdl.shaphar.net:8008/sap/bc/srt/rfc/sap/zchn_chp_ws_prnlist/200/zchn_chp_ws_prnlist/zchn_chp_ws_prnlist";
        String username = "SYSUSER_CHP";
        String password = "123456";
        SapWsUtils sapWsUtils = new SapWsUtils();
        String sendMsg = sapWsUtils.getSoapMsg("ZCHN_CHP_FM_PRNLIST", params);
        sapWsUtils.callWebService(INVOICE_WS_URL, sendMsg, username, password);*/

    }
}
