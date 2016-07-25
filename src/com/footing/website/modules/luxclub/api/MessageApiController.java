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
import com.footing.website.modules.luxclub.api.request.MessageReadRequest;
import com.footing.website.modules.luxclub.common.ApiConstant;
import com.footing.website.modules.luxclub.service.MessageService;
import com.footing.website.modules.luxclub.utils.ApiUtil;

/**
 * 客户经理-消息API
 * @author liuguoqing
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/api/message", method = RequestMethod.POST)
public class MessageApiController extends APIBaseController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MessageService messgaeService;
    
    /**
     * 个人信息接口
     * @param manager
     * @param response
     */
    @RequestMapping(value = "messagePageList", method = RequestMethod.POST)
    public void messagePageList(BaseRequest baseReq, HttpServletResponse response) {
        logger.info("=== 客户经理-个人信息接口,输入参数：{}", baseReq.toString());
        long begin = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        map = messgaeService.messagePageList(baseReq);
        String result = super.renderString(response, map);
        logger.info("=== 客户经理-个人信息接口, cost:{}ms, 输出参数：{}", (System.currentTimeMillis() - begin), result);
    }
    
    /**
     * 信息标记为已读
     * @param manager
     * @param response
     */
    @RequestMapping(value = "read", method = RequestMethod.POST)
    public void read(MessageReadRequest msgReadReq, HttpServletResponse response) {
        logger.info("=== 信息标记为已读接口,输入参数：{}", msgReadReq.toString());
        long begin = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> messages = ApiUtil.validateBean(msgReadReq);
        if (messages != null) {
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, messages.get(0), false);
        } else {
            map = messgaeService.read(msgReadReq);
        }
        String result = super.renderString(response, map);
        logger.info("=== 信息标记为已读接口, cost:{}ms, 输出参数：{}", (System.currentTimeMillis() - begin), result);
    }
    
    /**
     * 信息标记为已读
     * @param manager
     * @param response
     */
    @RequestMapping(value = "unreadCount", method = RequestMethod.POST)
    public void unreadCount(BaseRequest baseRequest, HttpServletResponse response) {
        logger.info("=== 信息标记为已读接口,输入参数：{}", baseRequest.toString());
        long begin = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        map = messgaeService.unreadCount(baseRequest);
        String result = super.renderString(response, map);
        logger.info("=== 信息标记为已读接口, cost:{}ms, 输出参数：{}", (System.currentTimeMillis() - begin), result);
    }
    
}
