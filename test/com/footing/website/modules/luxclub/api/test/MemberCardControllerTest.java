package com.footing.website.modules.luxclub.api.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.footing.website.common.mapper.JsonMapper;
import com.footing.website.common.utils.Base64Utils;
import com.footing.website.modules.luxclub.api.client.ClientProperty;

public class MemberCardControllerTest {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String url = "http://localhost:8080/luxclub/f/api/memberCard";
//    private String url = "http://fakeapi.fdjf.net:7006/luxclub/f/api/memberCard";
    
    @Test
    public void test(){
        //IOS
        String client = "ewogICJ0eXBlIiA6ICJpT1MiLAogICJ2ZXJzaW9uIiA6ICIxLjAiLAogICJpb3Mi\nIDogewogICAgImRldmljZU51bWJlciIgOiAiYjU4YzZhY2UxMTJiOGU5YTE5MjVl\nYjEyZDJmN2ZhZDAyNDZiMzA0NiIKICB9Cn0=";
        //android
        client = "eyJhbmRyb2lkIjp7ImNoYW5uZWwiOiIiLCJkZXZpY2VNb2RlbCI6IkFMRS1VTDAwIiwibWQ1IjoiRUY0MjEzMEIzMzMzN0U5MTZERUFCQjg0NjkzQUU0RjkiLCJzZGtWZXJzaW9uIjoiMjEiLCJzeXN0ZW1WZXJzaW9uIjoiNS4wLjIifSwibGFuZ3VhZ2UiOiJ6aCIsInR5cGUiOiJhbmRyb2lkIiwidmVyc2lvbiI6IjEuMCJ9";
        String jsonStr = Base64Utils.getDecodeBASE64(client);
        ClientProperty cProperty = (ClientProperty)JsonMapper.fromJsonString(jsonStr, new ClientProperty().getClass());
        if(cProperty == null){
            cProperty = new ClientProperty();
        }
        logger.info("{}",cProperty.toString());
    }

    /**
     * 绑定会员卡
     */
    @Test
    public void testBindMemberCard() {
        String cardno = "ECUW8723";
        String password = "aaaaaa";
        StringBuffer str = new StringBuffer();
        str.append("/bindMemberCard").append("?");
        str.append("cardno=").append(cardno).append("&");
        str.append("password=").append(password);
        
        String result = SendPost.sendPost(url + str.toString(), "");
        logger.info(">>> 绑定会员卡接口,返回参数:{}",result);
    }
    
    /**
     * 修改会员卡密码
     */
    @Test
    public void testUpdateCardPassword() {
        String cardno = "PZTL5636";
        String oldPassword = "zWAna7";
        String newPassword = "123456";
        String token = "a9138e7b000942d6a547aed34571c7ec";

        StringBuffer str = new StringBuffer();
        str.append("/updateCardPassword").append("?");
        str.append("cardno=").append(cardno).append("&");
        str.append("token=").append(token).append("&");
        str.append("oldPassword=").append(oldPassword).append("&");
        str.append("newPassword=").append(newPassword);

        String result = SendPost.sendPost(url + str.toString(), "");
        logger.info(">>> 会员卡密码修改接口,返回参数:{}",result);
    }

    /**
     * 会员卡信息
     */
    @Test
    public void testMemberInfo() {
        String cardno = "UCLP7729";
        String token = "999ff03088fd4bbabf786eb683d03e32";
        
        StringBuffer str = new StringBuffer();
        str.append("/memberInfo").append("?");
        str.append("cardno=").append(cardno).append("&");
        str.append("token=").append(token).append("&");
        
        String result = SendPost.sendPost(url + str.toString(), "");
        logger.info(">>> 会员卡信息接口,返回参数:{}",result);
    }
    
    /**
     * 解绑会员卡
     */
    @Test
    public void testUnBindMemberCard() {
        String cardno = "PZTL5636";
        String password = "123456";
        String token = "a6636b91de764ac288a39b3ba15e1603";
        
        StringBuffer str = new StringBuffer();
        str.append("/unbindMemberCard").append("?");
        str.append("cardno=").append(cardno).append("&");
        str.append("password=").append(password).append("&");
        str.append("token=").append(token);
        
        String result = SendPost.sendPost(url + str.toString(), "");
        logger.info(">>> 解绑会员卡接口,返回参数:{}",result);
    }

}
