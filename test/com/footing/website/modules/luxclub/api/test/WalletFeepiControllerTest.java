package com.footing.website.modules.luxclub.api.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WalletFeepiControllerTest {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

//    private String url = "http://localhost:8080/luxclub/f/api/walletFee";
    private String url = "http://fakeapi.fdjf.net:7006/luxclub/f/api/walletFee";
    
    /**
     * 消费/充值记录分页列表
     */
    @Test
    public void testWalletFeePageList() {
        String cardno = "MXEL3492";
        String token = "58a1f4a4617f45199870276d96f7ad5d";
        Integer type = 2;//费用类型(0:消费,1:充值)
        
        StringBuffer str = new StringBuffer();
        str.append("/walletFeePageList").append("?");
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
        String feeId = "1";
        String token = "de0993496df34dd382ed64e3a3549784";
        
        StringBuffer str = new StringBuffer();
        str.append("/walletFeeDetails").append("?");
        str.append("feeId=").append(feeId).append("&");
        str.append("token=").append(token).append("&");
        
        String result = SendPost.sendPost(url + str.toString(), "");
        logger.info(">>> 消费/充值记录详细信息接口,返回参数:{}",result);
    }

}
