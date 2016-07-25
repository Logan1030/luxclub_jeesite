package com.footing.website.modules.luxclub.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.footing.website.modules.luxclub.api.request.OrderRequest;
import com.footing.website.modules.luxclub.api.response.OrderManagerVo;
import com.footing.website.modules.luxclub.api.response.OrderVo;
import com.footing.website.modules.luxclub.common.ApiConstant;
import com.footing.website.modules.luxclub.common.BusinessConstants;
import com.footing.website.modules.luxclub.entity.CustomerClientToken;
import com.footing.website.modules.luxclub.service.OrderService;
import com.footing.website.modules.luxclub.utils.ApiUtil;
import com.footing.website.modules.luxclub.utils.CustomerClientUtils;

/**
 * 
 * <p>
 * Description:订单业务<br />
 * </p>
 * @title OrderController.java
 * @package com.footing.website.modules.luxclub.api 
 * @author yubin
 * @version 0.1 2016年3月22日
 */
@Controller
@RequestMapping(value="${frontPath}/api/manager" ,method = RequestMethod.POST)
public class OrderManagerApiController extends APIBaseController {
	
    private  final Logger logger=LoggerFactory.getLogger(getClass());
    @Autowired
    private OrderService orderService;
    /**
     * 
     * <p>
     * Description:取消-客户经理端<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年4月6日
     * @param response
     * @param orderRequest
     * void
     */
    @RequestMapping(value = "order/cancel")
    public void cancleOrder(HttpServletResponse response,OrderRequest orderRequest){
    	 
		Date startTime = new Date();
	    HashMap<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(orderRequest.getToken(), ApiConstant.API_CACHE_LOGIN_TOKEN_INVALID_TIME);
        if(clientToken != null){
            String result=orderService.cancleOrderByApp(orderRequest, clientToken,map);
            if(!BusinessConstants.SUCCESS.equals(result)){
            	ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, result, false);
            }
        }else{
        	//无效登录
            ApiUtil.tokenInvalidByCardNo(map);
        }
        printLog("取消订单", orderRequest, startTime, response, map);
	}
    /**
     * 
     * <p>
     * Description:确认-客户经理端<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年4月6日
     * @param response
     * @param orderRequest
     * void
     */
    @RequestMapping(value = "order/confirm")
    public void confirmOrderByApp(HttpServletResponse response,OrderRequest orderRequest){
		Date startTime = new Date();
	    HashMap<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(orderRequest.getToken(), ApiConstant.API_CACHE_LOGIN_TOKEN_INVALID_TIME);
        
        if(clientToken != null){
        	String result = orderService.confirmOrderByApp(orderRequest,clientToken ,map);
			 
            if(!BusinessConstants.SUCCESS.equals(result)){
            	ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, result, false);
            }
        }else{
        	//无效登录
            ApiUtil.tokenInvalidByCardNo(map);
        }
        printLog("确认订单接口", orderRequest, startTime, response, map);
	}
    /**
     * 
     * <p>
     * Description:待结账-客户经理端<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年4月6日
     * @param response
     * @param orderRequest
     * void
     */
    @RequestMapping(value = "order/checkout")
    public void unpayOrderByApp(HttpServletResponse response,OrderRequest orderRequest){
		Date startTime = new Date();
	    HashMap<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(orderRequest.getToken(), ApiConstant.API_CACHE_LOGIN_TOKEN_INVALID_TIME);
        String result="";
        if(clientToken != null){
            
            try {
				result = orderService.unpayOrderByApp(orderRequest,currentRequest(),clientToken,map);
			} catch (Exception e) {
				logger.error("待确认异常",e);
				result="网络异常，请稍后访问";
			}
            if(!BusinessConstants.SUCCESS.equals(result)){
            	ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, result, false);
            }
        }else{
        	//无效登录
            ApiUtil.tokenInvalidByCardNo(map);
        }
        printLog("待结账订单接口", orderRequest, startTime, response, map);
	}
    /**
     * 
     * <p>
     * Description:订单分页列表-客户经理端<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年4月6日
     * @param response
     * @param state
     * @param orderRequest
     * void
     */
    @RequestMapping(value="order/myOrder")
    public void queryOrderPageListBy(HttpServletResponse response,Integer state, OrderRequest orderRequest){
    	Date startTime = new Date();
	    HashMap<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(orderRequest.getToken(), ApiConstant.API_CACHE_LOGIN_TOKEN_INVALID_TIME);
        if(clientToken != null){
        	orderRequest.setCardno(clientToken.getAccount());
        	List<OrderVo> orderDetails=orderService.searchOrderPageList(orderRequest);
    		ApiUtil.mapRespData(map, orderDetails, "正常" , true);
        }else{
        	//无效登录
            ApiUtil.tokenInvalidByCardNo(map);
        }
        printLog("订单分页列表接口", orderRequest, startTime, response, map);
    }
    /**
     * 
     * <p>
     * Description:查看订单详细-客户经理端<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年4月6日
     * @param response
     * @param orderRequest
     * void
     */
    @RequestMapping(value="order/orderDetails")
	public void orderDetail(HttpServletResponse response,OrderRequest orderRequest){
    	Date startTime = new Date();
	    HashMap<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(orderRequest.getToken(), ApiConstant.API_CACHE_LOGIN_TOKEN_INVALID_TIME);
        if(clientToken != null){
        	    OrderManagerVo orderDetail=orderService.searchOrderDetailById(orderRequest.getOrderId());
        		if(orderDetail==null){
        			 ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "非法操作接口", false);
        		}else{
        			 ApiUtil.mapRespData(map, orderDetail, "正常" , true);
        		}
        }else{
        	//无效登录
            ApiUtil.tokenInvalidByCardNo(map);
        }
        printLog("查看订单详情接口", orderRequest, startTime, response, map);
         
	} 
}
