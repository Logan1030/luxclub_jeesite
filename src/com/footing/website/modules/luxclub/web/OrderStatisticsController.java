package com.footing.website.modules.luxclub.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.footing.website.common.persistence.Page;
import com.footing.website.common.web.BaseController;
import com.footing.website.modules.luxclub.entity.Order;
import com.footing.website.modules.luxclub.entity.OrderCnd;
import com.footing.website.modules.luxclub.entity.SiteInfo;
import com.footing.website.modules.luxclub.service.OrderService;
import com.footing.website.modules.luxclub.service.SiteInfoService;

/**
 * 
 * <p>
 * Description:订单统计<br />
 * </p>
 * @title OrderStatisticsController.java
 * @package com.footing.website.modules.luxclub.web 
 * @author yubin
 * @version 0.1 2016年3月18日
 */
@Controller
@RequestMapping(value = "${adminPath}/luxclub/orderstatistics")
public class OrderStatisticsController extends BaseController {
    
	@Autowired
	private OrderService orderService;
	@Autowired
	private SiteInfoService siteInfoService;
	@ModelAttribute
	public Order get(@RequestParam(required=false) Long id) {
		Order entity = null;
		if (id != null){
			entity = orderService.get(id);
		}
		if (entity == null){
			entity = new Order();
		}
		return entity;
	}
	/**
	 * 
	 * <p>
	 * Description:统计客户经理业绩<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月18日
	 * @param order
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * String
	 */
	@RequiresPermissions("luxclub:orderstatistics:view")
	@RequestMapping(value = {"list", ""})
	public String list(Order order, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Order> page = orderService.findPagebyStatistics(new Page<Order>(request, response), order); 
		List<SiteInfo> list = siteInfoService.findAllList();
		model.addAttribute("page", page);
		model.addAttribute("siteList", list);
		return "modules/luxclub/orderstatisticsList";
	}
	/**
	 * 
	 * <p>
	 * Description:统计账务信息<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年3月18日
	 * @param order
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * String
	 */
	@RequiresPermissions("luxclub:orderstatistics:view")
	@RequestMapping(value ="listInfo")
	public String orderstatistics(OrderCnd ordercnd, HttpServletRequest request, HttpServletResponse response, Model model) {
		 
		model.addAttribute("queryPayAmount", orderService.queryPayAmount(ordercnd));
		model.addAttribute("queryUnpayAmount", orderService.queryUnpayAmount(ordercnd));
		
		return "modules/luxclub/orderstatistics";
	} 
	@RequiresPermissions("luxclub:orderstatistics:view")
	@RequestMapping(value ="flot")
	public String flot(OrderCnd ordercnd, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/luxclub/flot";
	}
}