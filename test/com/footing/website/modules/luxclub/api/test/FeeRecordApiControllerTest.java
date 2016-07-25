package com.footing.website.modules.luxclub.api.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeeRecordApiControllerTest {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String url = "http://localhost:8080/luxclub/f/api/feeRecord";
//    private String url = "http://fakeapi.fdjf.net:7006/luxclub/f/api/feeRecord";
   /* {
        cardno = LRBT6275;
        client = "ewogICJ0eXBlIiA6ICJpT1MiLAogICJ2ZXJzaW9uIiA6ICIxLjAiLAogICJpb3Mi
    \nIDogewogICAgImRldmljZU51bWJlciIgOiAiYTI1MzczZjYzYmE0ZDAyM2I2ODQy
    \nMTQ3YTUzMzcyZmI1Y2FmZTljZCIKICB9Cn0=";
        feeType = 0;
        pageNumber = 1;
        pageSize = 10;
        token = 4e6a5d5a252f4dd19e25fd96a4c336a6;
    }*/
    
    /**
     * 消费/充值记录分页列表
     */
    @Test
    public void testFeeRecordPageList() {
        String cardno = "mjDt9957";
        String token = "de0993496df34dd382ed64e3a3549784";
        Integer type = 0;//费用类型(0:消费,1:充值)
        
        StringBuffer str = new StringBuffer();
        str.append("/feeRecordPageList").append("?");
        str.append("cardno=").append(cardno).append("&");
        str.append("feeType=").append(type).append("&");
        str.append("pageSize=").append(30).append("&");
        str.append("pageNumber=").append(1).append("&");
        str.append("token=").append(token);
        
        String result = SendPost.sendPost(url + str.toString(), "");
        logger.info(">>> 消费/充值记录分页列表接口,返回参数:{}",result);
    }


    /**
     * 消费/充值记录详细信息
     */
    @Test
    public void testFeeRecordDetails() {
        String feeId = "10";
        String token = "de0993496df34dd382ed64e3a3549784";
        
        StringBuffer str = new StringBuffer();
        str.append("/feeRecordDetails").append("?");
        str.append("feeId=").append(feeId).append("&");
        str.append("token=").append(token).append("&");
        
        String result = SendPost.sendPost(url + str.toString(), "");
        logger.info(">>> 消费/充值记录详细信息接口,返回参数:{}",result);
    }

}
