package com.footing.website.modules.luxclub.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.footing.website.modules.luxclub.common.BusinessConstants;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.footing.website.common.config.Global;
import com.footing.website.common.persistence.Page;
import com.footing.website.common.web.BaseController;
import com.footing.website.modules.luxclub.entity.Order;
import com.footing.website.modules.luxclub.entity.SiteInfo;
import com.footing.website.modules.luxclub.service.OrderService;
import com.footing.website.modules.luxclub.service.SiteInfoService;

/**
 * 
 * <p>
 * Description:派单控制层<br />
 * </p>
 * @title SendOrderController.java
 * @package com.footing.website.modules.luxclub.web 
 * @author yubin
 * @version 0.1 2016年3月17日
 */
@Controller
@RequestMapping(value = "${adminPath}/luxclub/sendorder")
public class SendOrderController extends BaseController {
    
	private static final Logger log=Logger.getLogger(SendOrderController.class);
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
	
	@RequiresPermissions("luxclub:sendorder:view")
	@RequestMapping(value = {"list", ""})
	public String list(Order order, HttpServletRequest request, HttpServletResponse response, Model model) {
		order.setState("0");
		Page<Order> page = orderService.findPage(new Page<Order>(request, response), order); 
		model.addAttribute("page", page);
		return "modules/luxclub/sendorderList";
	}

	@RequiresPermissions("luxclub:sendorder:view")
	@RequestMapping(value = "form")
	public String form(Order order, Model model) {
		model.addAttribute("order", order);
		List<SiteInfo> list = siteInfoService.findAllList();
		model.addAttribute("siteList", list);
		return "modules/luxclub/sendorderForm";
	}

	@RequiresPermissions("luxclub:sendorder:edit")
	@RequestMapping(value = "save")
	public String save(Order order, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, order)){
			return form(order, model);
		}
		String result="";
		try {
			result=orderService.sendOrder(order);
		} catch (Exception e) {
			log.info("取消订单异常：", e);
		}
		if(BusinessConstants.SUCCESS.equals(result)){
			addMessage(redirectAttributes, "保存订单表成功");
		}else{
			addMessage(redirectAttributes, result);
		}
		return "redirect:"+Global.getAdminPath()+"/luxclub/sendorder/?repage";
	}
	
	@RequiresPermissions("luxclub:sendorder:edit")
	@RequestMapping(value = "delete")
	public String delete(Order order, RedirectAttributes redirectAttributes) {
		orderService.delete(order);
		addMessage(redirectAttributes, "删除订单表成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/sendorder/?repage";
	}
	
}