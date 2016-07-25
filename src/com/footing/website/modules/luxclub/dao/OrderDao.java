package com.footing.website.modules.luxclub.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.footing.website.common.persistence.CrudDao;
import com.footing.website.common.persistence.annotation.MyBatisDao;
import com.footing.website.modules.luxclub.api.response.OrderManagerVo;
import com.footing.website.modules.luxclub.api.response.OrderVo;
import com.footing.website.modules.luxclub.entity.Order;
import com.footing.website.modules.luxclub.entity.OrderCnd;
import com.footing.website.modules.luxclub.entity.Statistical;

/**
 * 订单表DAO接口
 * @author yubin
 * @version 2016-03-16
 */
@MyBatisDao
public interface OrderDao extends CrudDao<Order> {
	
	public Integer countOrder();
	/**
	 * 
	 * <p>
	 * Description:20分钟后订单状态重新置为预定状态 <br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月17日
	 * void
	 */
	public void sendOrderQuartz();
	/**
	 * 
	 * <p>
	 * Description:查询20分钟后订单未处理的订单<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年4月5日
	 * @return
	 * List<Order>
	 */
	public List<Order>findBysendOrderQuertz();
	/**
	 * 
	 * <p>
	 * Description:统计表<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年4月5日
	 * @param order
	 * @return
	 * List<Order>
	 */
	public List<Order>findPagebyStatistics(Order order);
	
	public BigDecimal queryPayAmount(OrderCnd orderCnd);
	
	public BigDecimal queryUnpayAmount(OrderCnd orderCnd);
	/**
	 * 
	 * <p>
	 * Description:用户端-查询详细订单<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月22日
	 * @param id
	 * @return
	 * OrderDetail
	 */
	public OrderVo queryOrderDetailById(@Param("id")long id);
	/**
	 * 
	 * <p>
	 * Description:用户端-查询订单分页列表<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月23日
	 * @return
	 * List<OrderDetail>
	 */
	public List<OrderVo>queryOrderPageList(@Param("cardno")String cardno,
			@Param("pageSize")int pageSize,@Param("pageNumber")int pageNumber,@Param("state")Integer state);
	/**
	 * 
	 * <p>
	 * Description:客户经理端-查询详细订单<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月22日
	 * @param id
	 * @return
	 * OrderDetail
	 */
	public OrderManagerVo searchOrderDetailById(@Param("id")long id);
	/**
	 *  
	 * <p>
	 * Description:客户经理端-查询订单分页列表<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年4月6日
	 * @param state
	 * @param cardno
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 * List<OrderVo>
	 */
	public List<OrderVo>searchOrderPageList(@Param("state")int state,@Param("bid")String cardno,
			@Param("pageSize")int pageSize,@Param("pageNumber")int pageNumber);
	
    /**
     * 根据卡号获取未完成的订单数量
     * @param paramsMap<卡号，未完成的订单状态>
     * @return
     */
    public int countNotFinishOrderNumber(Map<String,Object> paramsMap);
    
    public List<Statistical> statisticalStatement();
    
    public Order lockOrderById(long id);
    /**
     * 
     * <p>
     * Description:根据状态查询订单<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年4月6日
     * @param id
     * @param state
     * @return
     * Order
     */
    public Order getOrderBystatus(@Param("id")long id,@Param("state")String state);
	
}