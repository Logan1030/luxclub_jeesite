package com.footing.website.modules.luxclub.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footing.website.common.service.CrudService;
import com.footing.website.common.utils.StringUtils;
import com.footing.website.modules.luxclub.api.request.BaseRequest;
import com.footing.website.modules.luxclub.api.request.MessageReadRequest;
import com.footing.website.modules.luxclub.api.request.PageBean;
import com.footing.website.modules.luxclub.api.response.MessageResp;
import com.footing.website.modules.luxclub.common.ApiConstant;
import com.footing.website.modules.luxclub.dao.MessageDao;
import com.footing.website.modules.luxclub.entity.CustomerClientToken;
import com.footing.website.modules.luxclub.entity.MessageNotify;
import com.footing.website.modules.luxclub.message.MessageConstant;
import com.footing.website.modules.luxclub.utils.ApiUtil;
import com.footing.website.modules.luxclub.utils.CustomerClientUtils;

/**
 * 客户经理--消息服务
 * @author liuguoqing
 *
 */
@Service
@Transactional(readOnly = true)
public class MessageService extends CrudService<MessageDao, MessageNotify> {

    /**
     * 个人信息
     * @param baseReq
     * @return
     */
    public Map<String, Object> messagePageList(BaseRequest baseReq) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断token
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(baseReq.getToken(), ApiConstant.API_CACHE_LOGIN_TOKEN_INVALID_TIME);
        if (clientToken != null) {
            PageBean pageBean = new PageBean(baseReq.getPageNumber(), baseReq.getPageSize());
            List<MessageResp> resultList = dao.messagePageList(clientToken.getAccount(), pageBean.getStartNumber(), pageBean.getEndNumber());
            for (MessageResp message : resultList) {
                String statusName = 2 == message.getStatus() ? "已读" : "未读";
                message.setStatusName(statusName);
            }
            ApiUtil.mapRespData(map, resultList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        } else {
            ApiUtil.tokenInvalidByLogin(map);
        }
        return map;
    }

    /**
     * 消息标记为已读
     * @param msgReadReq
     * @return
     */
    @Transactional(readOnly = false)
    public Map<String, Object> read(MessageReadRequest msgReadReq) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断token
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(msgReadReq.getToken(), ApiConstant.API_CACHE_LOGIN_TOKEN_INVALID_TIME);
        if (clientToken != null) {
            String[] idsStr = StringUtils.split(msgReadReq.getMessageIds(), ",");
            dao.batchUpdateStatus(idsStr, MessageConstant.MESSAGE_STATUS_READ);
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        } else {
            ApiUtil.tokenInvalidByLogin(map);
        }
        return map;
    }

    /**
     * 未读消息数量
     * @param msgReadReq
     * @return
     */
    @Transactional(readOnly = false)
    public Map<String, Object> unreadCount(BaseRequest msgReadReq) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断token
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(msgReadReq.getToken(), ApiConstant.API_CACHE_LOGIN_TOKEN_INVALID_TIME);
        if (clientToken != null) {
            int count = dao.getUnreadCount(clientToken.getAccount());
            ApiUtil.mapRespData(map, count, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        } else {
            ApiUtil.tokenInvalidByLogin(map);
        }
        return map;
    }

}
