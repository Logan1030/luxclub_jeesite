package com.footing.website.modules.luxclub.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.footing.website.modules.luxclub.api.request.BaseRequest;
import com.footing.website.modules.luxclub.api.request.ManagerPasswordRequest;
import com.footing.website.modules.luxclub.api.request.ManagerRequest;
import com.footing.website.modules.luxclub.common.ApiConstant;
import com.footing.website.modules.luxclub.service.ManagerService;
import com.footing.website.modules.luxclub.utils.ApiUtil;

/**
 * 客户经理端API
 * @author liuguoqing
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/api/manager", method = RequestMethod.POST)
public class ManagerApiController extends APIBaseController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ManagerService managerService;
    
    /**
     * 登录接口
     * @param manager
     * @param response
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public void login(ManagerRequest manager, HttpServletResponse response) {
        logger.info("=== 客户经理登录接口,输入参数：{}", manager.toString());
        long begin = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> messages = ApiUtil.validateBean(manager);
        if (messages != null) {
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, messages.get(0), false);
        } else {
            map = managerService.login(manager);
        }
        String result = super.renderString(response, map);
        logger.info("=== 客户经理登录接口, cost:{}ms, 输出参数：{}", (System.currentTimeMillis() - begin), result);
    }
    
    /**
     * 修改密码接口
     * @param manager
     * @param response
     */
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public void updatePassword(ManagerPasswordRequest managerPwd, HttpServletResponse response) {
        logger.info("=== 客户经理修改密码接口,输入参数：{}", managerPwd.toString());
        long begin = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> messages = ApiUtil.validateBean(managerPwd);
        if (messages != null) {
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, messages.get(0), false);
        } else {
            map = managerService.updateManagerPassword(managerPwd);
        }
        String result = super.renderString(response, map);
        logger.info("=== 客户经理修改密码接口, cost:{}ms, 输出参数：{}", (System.currentTimeMillis() - begin), result);
    }
    
    /**
     * 登录接口
     * @param manager
     * @param response
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public void logout(BaseRequest baseReq, HttpServletResponse response) {
        logger.info("=== 客户经理登录接口,输入参数：{}", baseReq.toString());
        long begin = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        map = managerService.logout(baseReq.getToken());
        String result = super.renderString(response, map);
        logger.info("=== 客户经理登录接口, cost:{}ms, 输出参数：{}", (System.currentTimeMillis() - begin), result);
    }

}
