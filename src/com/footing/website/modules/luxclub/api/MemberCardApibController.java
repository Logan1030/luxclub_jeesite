package com.footing.website.modules.luxclub.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.footing.website.modules.luxclub.api.request.BaseRequest;
import com.footing.website.modules.luxclub.api.request.CardPasswordRequest;
import com.footing.website.modules.luxclub.api.request.MemberCardRequest;
import com.footing.website.modules.luxclub.common.ApiConstant;
import com.footing.website.modules.luxclub.service.MemberInfoService;
import com.footing.website.modules.luxclub.utils.ApiUtil;

/**
 * 会员卡接口
 * @author liuguoqing
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/api/memberCard", method = RequestMethod.POST)
public class MemberCardApibController extends APIBaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemberInfoService memberInfoService;

    /**
     * 绑定会员卡接口
     * @param memberCard
     * @param response
     */
    @RequestMapping(value = "bindMemberCard", method = RequestMethod.POST)
    public void bindMemberCard(MemberCardRequest memberCard, HttpServletResponse response) {
        logger.info("=== 会员卡绑定接口,输入参数：{}", memberCard.toString());
        long begin = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> messages = ApiUtil.validateBean(memberCard);
        if (messages != null) {
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, messages.get(0), false);
        } else {
            memberCard.setCardno(memberCard.getCardno().toUpperCase());//转大写
            map = memberInfoService.bindMemberCard(memberCard);
        }
        String result = super.renderString(response, map);
        logger.info("=== 会员卡绑定接口, cost:{}ms, 输出参数：{}", (System.currentTimeMillis() - begin), result);
    }

    /**
     * 解绑会员卡接口
     * @param memberCard
     * @param response
     * @return
     */
    @RequestMapping(value = "unbindMemberCard", method = RequestMethod.POST)
    public void unbindMemberCard(MemberCardRequest memberCard, HttpServletResponse response) {
        logger.info("=== 解绑会员卡接口,输入参数：{}", memberCard.toString());
        long begin = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> messages = ApiUtil.validateBean(memberCard);
        if (messages != null) {
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, messages.get(0), false);
        } else {
            memberCard.setCardno(memberCard.getCardno().toUpperCase());//转大写
            map = memberInfoService.unbindMemberCard(memberCard);
        }

        String result = super.renderString(response, map);
        logger.info("=== 解绑会员卡接口, cost:{}ms, 输出参数：{}", (System.currentTimeMillis() - begin), result);
    }
    
    /**
     * 修改密码
     * @param cardPwdReq
     * @param response
     */
    @RequestMapping(value = "updateCardPassword", method = RequestMethod.POST)
    public void updateCardPassword(CardPasswordRequest cardPwdReq, HttpServletResponse response) {
        logger.info("=== 修改会员卡密码接口, 输入参数：{}", cardPwdReq);
        long begin = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> messages = ApiUtil.validateBean(cardPwdReq);
        if (messages != null) {
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, messages.get(0), false);
        } else {
            cardPwdReq.setCardno(cardPwdReq.getCardno().toUpperCase());//转大写
            map = memberInfoService.updateCardPassword(cardPwdReq);
        }
        String result = super.renderString(response, map);
        logger.info("=== 修改会员卡密码接口, cost:{}ms, 输出参数：{}", (System.currentTimeMillis() - begin), result);
    }

    /**
     * 根据卡号获取会员卡信息
     * @param cardno
     * @param token
     * @param response
     */
    @RequestMapping(value = "memberInfo", method = RequestMethod.POST)
    public void memberInfo(String cardno, BaseRequest base, HttpServletResponse response) {
        logger.info("===获取会员卡信息接口, 输入参数：cardno:{}, {}", cardno, base);
        long begin = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(cardno)){
            map = memberInfoService.findMemberInfo(cardno.toUpperCase(), base.getClient(), base.getToken());
        }else{
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "卡号不能为空", false);
        }
        String result = super.renderString(response, map);
        logger.info("=== 获取会员卡信息接口, cost:{}ms, 输出参数：{}", (System.currentTimeMillis() - begin), result);
    }
}
