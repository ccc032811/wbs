package com.neefull.fsp.web.job.task;

import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.job.entity.AdminJob;
import com.neefull.fsp.web.job.mapper.AdminJobMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: chengchengchu
 * @Date: 2020/2/7  18:12
 */
@Slf4j
@Component
public class Monotor {

    @Autowired
    private AdminJobMapper jobMapper;

    public void monotor(String params) throws FebsException {
        String url = params+"/actuator/health";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    AdminJob job = new AdminJob();
                    job.setParams(params);
                    job.setIsalive("0");
                    jobMapper.updateJob(job);
                    System.out.println("Response content: " + EntityUtils.toString(entity));
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            AdminJob job = new AdminJob();
            job.setParams(params);
            job.setIsalive("1");
            jobMapper.updateJob(job);
            throw new FebsException("连接失败");
        }finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
