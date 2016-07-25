package com.footing.website.modules.luxclub.api.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManagerControllerTest {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

//    private String url = "http://localhost:8080/luxclub/f/api/manager";
    private String url = "http://fakeapi.fdjf.net:7006/luxclub/f/api/manager";
    

    /**
     * 登录
     */
    @Test
    public void testLogin() {
        String loginName = "thinkgem";
        String password = "admin";
        StringBuffer str = new StringBuffer();
        str.append("/login").append("?");
        str.append("loginName=").append(loginName).append("&");
        str.append("password=").append(password);
        
        String result = SendPost.sendPost(url + str.toString(), "");
        logger.info(">>> 客户经理登录接口,返回参数:{}",result);
    }
    
    /**
     * 修改密码
     */
    @Test
    public void testUpdatePassword() {
        String token = "a097421df07f45119e151a9585adce30";
        String oldPassword = "123456";
        String newPassword = "admin";

        StringBuffer str = new StringBuffer();
        str.append("/updatePassword").append("?");
        str.append("token=").append(token).append("&");
        str.append("oldPassword=").append(oldPassword).append("&");
        str.append("newPassword=").append(newPassword);

        String result = SendPost.sendPost(url + str.toString(), "");
        logger.info(">>> 会员卡密码修改接口,返回参数:{}",result);
    }
    
    /**
     * 登出
     */
    @Test
    public void testLogout() {
        String token = "ba31bf58d3844de9a6c387dcfbd973ce";
        
        StringBuffer str = new StringBuffer();
        str.append("/logout").append("?");
        str.append("token=").append(token);
        
        String result = SendPost.sendPost(url + str.toString(), "");
        logger.info(">>> 登出接口,返回参数:{}",result);
    }

}
