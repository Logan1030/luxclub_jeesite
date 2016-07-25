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

import com.footing.website.modules.luxclub.api.client.ClientProperty;
import com.footing.website.modules.luxclub.api.request.OrderRequest;
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
@RequestMapping(value="${frontPath}/api/orderBusiness" ,method = RequestMethod.POST)
public class OrderApiController extends APIBaseController {
	
    @Autowired
    private OrderService orderService;
    protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 *  
	 * <p>
	 * Description:提交订单<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月22日
	 * @param response
	 * @param client
	 * @param token
	 * @param orderRequest
	 * @return
	 * String
	 */
    @RequestMapping(value="submitOrder")
	public void submitOrder(HttpServletResponse response,OrderRequest orderRequest){
    	 
		Date startTime = new Date();
	    HashMap<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(orderRequest.getToken());
        ClientProperty cProperty = ApiUtil.getClient(orderRequest.getClient());
       
            try {
				String result=orderService.commitOrderByApp(orderRequest, cProperty,map);
			} catch (Exception e) {
				logger.error("提交表单",e);
			}
          
        printLog("提交订单", orderRequest, startTime, response, map);
	}
    /**
     * 
     * <p>
     * Description:查看订单详情<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年3月22日
     * @param response
     * @param client
     * @param token
     * @param orderId
     * @return
     * String
     */
    @RequestMapping(value="orderDetails")
	public void orderDetails(HttpServletResponse response,OrderRequest orderRequest){
    	 
    	Date startTime = new Date();
	    HashMap<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(orderRequest.getToken());
        if(clientToken != null){
        	    OrderVo orderDetail=orderService.queryOrderDetailById(orderRequest.getOrderId());
        		if(orderDetail==null){
        			 ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "非法操作接口", false);
        		}else{
        			 ApiUtil.mapRespData(map, orderDetail, "正常" , true);
        		}
        }else{
        	//无效登录
            ApiUtil.tokenInvalidByCardNo(map);
        }
        printLog("查看订单详情", orderRequest, startTime, response, map);
         
	}
    /**
     * 
     * <p>
     * Description:查询订单列表<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年3月23日
     * @param response
     * @param client
     * @param token
     * @param cardno
     * @param pageSize
     * @param pageNumber
     * @return
     * String
     */
    @RequestMapping(value="queryOrderPageList" )
    public void queryOrderPageList(HttpServletResponse response, OrderRequest orderRequest){
    	 
    	Date startTime = new Date();
	    HashMap<String, Object> map = new HashMap<String, Object>();
        CustomerClientToken clientToken = CustomerClientUtils.getByToken(orderRequest.getToken());
        if(clientToken != null){
        	List<OrderVo> orderDetails=orderService.queryOrderPageList(orderRequest);
    		if(orderDetails==null){
    			 ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "非法操作接口", false);
    		}else{
    			 ApiUtil.mapRespData(map, orderDetails, "正常" , true);
    		}
        }else{
        	//无效登录
            ApiUtil.tokenInvalidByCardNo(map);
        }
        printLog("查询订单列表", orderRequest, startTime, response, map);
    }
}
