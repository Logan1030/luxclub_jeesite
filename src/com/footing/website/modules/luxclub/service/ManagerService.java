package com.footing.website.modules.luxclub.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footing.website.common.service.CrudService;
import com.footing.website.common.utils.IdGen;
import com.footing.website.common.utils.StringUtils;
import com.footing.website.modules.entity.User;
import com.footing.website.modules.luxclub.api.client.ClientProperty;
import com.footing.website.modules.luxclub.api.request.ManagerPasswordRequest;
import com.footing.website.modules.luxclub.api.request.ManagerRequest;
import com.footing.website.modules.luxclub.api.response.LoginResp;
import com.footing.website.modules.luxclub.api.response.TokenResp;
import com.footing.website.modules.luxclub.common.ApiConstant;
import com.footing.website.modules.luxclub.dao.ManagerDao;
import com.footing.website.modules.luxclub.entity.CustomerClientToken;
import com.footing.website.modules.luxclub.utils.ApiUtil;
import com.footing.website.modules.luxclub.utils.ClientCacheUtils;
import com.footing.website.modules.luxclub.utils.CustomerClientUtils;
import com.footing.website.modules.sys.service.SystemService;

@Service
@Transactional(readOnly = true)
public class ManagerService extends CrudService<ManagerDao, User> {
    
    @Autowired
    private CustomerClientTokenService customerClientTokenService;
    
    /**
     * 客户经理登录
     * 1、验证登录名是否存在
     * 2、验证密码是否正确
     * 3、添加到客户端缓存，并获取token
     * 4、更新登录信息
     * @param map
     * @param client
     * @param manager
     */
    @Transactional(readOnly = false)
    public Map<String, Object> login(ManagerRequest manager) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = dao.getByLoginName(manager.getLoginName());
        if (user != null) {
            //验证密码
            if (SystemService.validatePassword(manager.getPassword(), user.getPassword())) {
                //登录成功
                //获取token
                String token = this.getLoginToken(user.getId()+"", manager.getClient());
                if (StringUtils.isNotBlank(token)) {
                    //获取终端类型
                    ClientProperty client = ApiUtil.getClient(manager.getClient());
                    int terminalType = ApiConstant.OP_TERM_DICT_WEBSITE_TYPE;//默认 web
                    if(client != null && StringUtils.isNotBlank(client.getType())){
                        if(client.getType().toUpperCase().equals(ApiConstant.OP_TERM_DICT_IOS)){
                            terminalType = ApiConstant.OP_TERM_DICT_IOS_TYPE;
                        }else if(client.getType().toUpperCase().equals(ApiConstant.OP_TERM_DICT_ANDROID)){
                            terminalType = ApiConstant.OP_TERM_DICT_ANDROID_TYPE;
                        }
                    }
                    //更新终端类型、登录时间
                    dao.updateLoginInfo(user.getId(), terminalType, new Date());
                    LoginResp data = new LoginResp();
                    data.setUserId(user.getId());
                    data.setToken(token);
                    ApiUtil.mapRespData(map, data, "用户登录成功", true);
                } else {
                    ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "用户登录失败", false);
                }
            } else {
                ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "用户名或密码错误", false);
            }
        } else {
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "用户名或密码错误", false);
        }
        return map;
    }
    
    /**
     * 获取登录token
     * @param account
     * @param opTerm
     * @return
     */
    private String getLoginToken(String account, String client) {
        //获取app端类型
        ClientProperty cProperty = ApiUtil.getClient(client);
        String type = ApiUtil.getOperaChannel(cProperty.getType());
        
        CustomerClientToken clientToken = new CustomerClientToken();
        clientToken.setAccount(account);
        clientToken.setTermType(type);
        clientToken.setToken(IdGen.uuid());
        clientToken.setLastDate(new Date());
        String token = customerClientTokenService.operaCustomerClientToken(clientToken);
        return token;
    }
    
    /**
     * 修改密码
     * 1、验证token是否有效
     * 2、验证客户经理信息是否存在
     * 3、验证旧密码是否正确
     * 4、生成新的token并更新客户端缓存记录
     * @param passwordReq
     * @return
     */
    @Transactional(readOnly = false)
    public Map<String, Object> updateManagerPassword(ManagerPasswordRequest passwordReq) {
        Map<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(passwordReq.getToken(), ApiConstant.API_CACHE_LOGIN_TOKEN_INVALID_TIME);
        if (clientToken != null) {
            User user = dao.get(Long.parseLong(clientToken.getAccount()));
            if (user != null) {
                //验证密码
                if (SystemService.validatePassword(passwordReq.getOldPassword(), user.getPassword())) {
                    // 修改会员卡密码
                    this.updatePwd(map, clientToken, user, passwordReq.getNewPassword(), passwordReq.getClient());
                } else {
                    ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "旧密码不正确", false);
                }
            } else {
                ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "客户经理账号不正确", false);
            }
        } else {
            ApiUtil.tokenInvalidByLogin(map);
        }
        return map;
    }

    /**
     * 修改密码
     * @param map
     * @param oldToken
     * @param user
     * @param newPassword
     * @param client
     */
    private void updatePwd(Map<String, Object> map, CustomerClientToken oldToken, User user, String newPassword, String client) {
        boolean result = dao.updatePassword(user.getId(), SystemService.entryptPassword(newPassword));
        if (result) {
            String newToken = IdGen.uuid();// token
            CustomerClientToken clientToken = new CustomerClientToken();
            clientToken.setAccount(user.getId()+"");
            clientToken.setTermType(oldToken.getTermType());
            clientToken.setToken(newToken);
            customerClientTokenService.update(clientToken);
            // 清除缓存
            CustomerClientToken customerClientToken = (CustomerClientToken) ClientCacheUtils.get(CustomerClientUtils.CLIENT_CACHE, CustomerClientUtils.CLIENT_CACHE_TOKEN_ + oldToken.getToken());
            if (customerClientToken != null) {
                CustomerClientUtils.clearCache(customerClientToken);
            }
            TokenResp data = new TokenResp();
            data.setToken(newToken);
            ApiUtil.mapRespData(map, data, "修改密码成功", true);
        } else {
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "修改密码失败", false);
        }
    }
    
    /**
     * 登出
     * @param token
     * @return
     */
    @Transactional(readOnly = false)
    public Map<String, Object> logout(String token){
        Map<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken customerClientToken = (CustomerClientToken)ClientCacheUtils.get(CustomerClientUtils.CLIENT_CACHE, CustomerClientUtils.CLIENT_CACHE_TOKEN_ + token);
        if(customerClientToken != null){
            CustomerClientUtils.clearCache(customerClientToken);
            Date date = DateUtils.addMinutes(new Date(), ApiConstant.API_CACHE_LOGIN_TOKEN_INVALID_TIME);
            customerClientToken.setLastDate(date);
            int count = customerClientTokenService.update(customerClientToken);
            if(count > 0){
                TokenResp data = new TokenResp();
                data.setToken("");
                ApiUtil.mapRespData(map, data, "登出成功", true);
            }else{
                ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "登出失败", false);
            }
        }else{
            ApiUtil.tokenInvalidByLogin(map);
        }
        return map;
    }

}
