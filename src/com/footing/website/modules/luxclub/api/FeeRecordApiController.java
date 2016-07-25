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
import com.footing.website.modules.luxclub.api.request.FeeRecordRequest;
import com.footing.website.modules.luxclub.api.response.FeeRecordResp;
import com.footing.website.modules.luxclub.common.ApiConstant;
import com.footing.website.modules.luxclub.entity.CustomerClientToken;
import com.footing.website.modules.luxclub.service.FeeRecordService;
import com.footing.website.modules.luxclub.utils.ApiUtil;
import com.footing.website.modules.luxclub.utils.CustomerClientUtils;

/**
 * 会员卡消费/充值记录接口
 * @author liuguoqing
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/api/feeRecord", method = RequestMethod.POST)
public class FeeRecordApiController extends APIBaseController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private FeeRecordService feeRecordService;
    
    /**
     * 消费/充值记录分页列表
     * @param baseReq
     * @param feeType
     * @param response
     */
    @RequestMapping(value = "feeRecordPageList", method = RequestMethod.POST)
    public void feeRecordPageList(FeeRecordRequest feeRecordReq, HttpServletResponse response){
        logger.info("=== 消费/充值记录分页列表接口,输入参数：{}", feeRecordReq.toString());
        long begin = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> messages = ApiUtil.validateBean(feeRecordReq);
        if (messages != null) {
            ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, messages.get(0), false);
        }else{
            CustomerClientToken clientToken = CustomerClientUtils.getByToken(feeRecordReq.getToken());
            if(clientToken != null ){
                feeRecordReq.setCardno(feeRecordReq.getCardno().toUpperCase());//卡号转大写
                List<FeeRecordResp> feeList = feeRecordService.findFeeRecordPageList(feeRecordReq);
                ApiUtil.mapRespData(map, feeList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
            }else{
                ApiUtil.tokenInvalidByCardNo(map);
                // 清理缓存信息
                CustomerClientUtils.clearCache(clientToken);
            }
        }
        String result = super.renderString(response, map);
        logger.info("=== 消费/充值记录分页列表接口, cost:{}ms, 输出参数：{}", (System.currentTimeMillis() - begin), result);
    }

    /**
     * 消费/充值记录分页列表
     * @param baseReq
     * @param feeType
     * @param response
     */
    @RequestMapping(value = "feeRecordDetails", method = RequestMethod.POST)
    public void feeRecordDetails(Long feeId, BaseRequest baseRquest, HttpServletResponse response){
        logger.info("=== 消费/充值记录详细信息接口,输入参数：feeId:{}, {}", feeId, baseRquest.toString());
        long begin = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(baseRquest.getToken());
        if(clientToken != null ){
            FeeRecordResp feeRecordResp = feeRecordService.feeRecordDetails(feeId);
            ApiUtil.mapRespData(map, feeRecordResp, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
        }else{
            ApiUtil.tokenInvalidByCardNo(map);
            // 清理缓存信息
            CustomerClientUtils.clearCache(clientToken);        
        }
        String result = super.renderString(response, map);
        logger.info("=== 消费/充值记录详细信息接口, cost:{}ms, 输出参数：{}", (System.currentTimeMillis() - begin), result);
    }

}
