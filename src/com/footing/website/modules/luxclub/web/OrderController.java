package com.footing.website.modules.luxclub.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.footing.website.modules.luxclub.common.BusinessConstants;
import com.footing.website.modules.luxclub.dao.MemberInfoDao;
import com.footing.website.modules.luxclub.entity.MemberInfo;
import com.footing.website.modules.luxclub.entity.Order;
import com.footing.website.modules.luxclub.entity.SiteInfo;
import com.footing.website.modules.luxclub.service.OrderService;
import com.footing.website.modules.luxclub.service.SiteInfoService;

/**
 * 订单表Controller
 * @author yubin
 * @version 2016-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/luxclub/order")
public class OrderController extends BaseController {
    
	private static final Logger log=Logger.getLogger(OrderController.class);
	@Autowired
	private OrderService orderService;
    @Autowired
    private SiteInfoService siteInfoService;
	@Autowired
	private MemberInfoDao memberInfoDao;
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
	
	@RequiresPermissions("luxclub:order:view")
	@RequestMapping(value = {"list", ""})
	public String list(Order order, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Order> page = orderService.findPage(new Page<Order>(request, response), order); 
		String path = request.getContextPath();
		path=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		System.out.println(path);
		model.addAttribute("page", page);
		return "modules/luxclub/orderList";
	}

	@RequiresPermissions("luxclub:order:view")
	@RequestMapping(value = "form")
	public String form(Order order, Model model) {
		List<SiteInfo> list = siteInfoService.findAllList();
		model.addAttribute("siteList", list);
		MemberInfo memberInfo=memberInfoDao.queryMemberInfoByCardno(order.getMemberCardno(),null);
		model.addAttribute("order", order);
		model.addAttribute("memberInfo", memberInfo);
		return "modules/luxclub/orderDetail";
	}

	@RequiresPermissions("luxclub:order:edit")
	@RequestMapping(value = "save")
	public String save(Order order, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, order)){
			return form(order, model);
		}
		String result="";
		try {
			 result=orderService.saveOrderAndOrderDetail(order);
		} catch (Exception e) {
			log.info("订单表异常：", e);
		}
		if(result.equals(BusinessConstants.SUCCESS)){
		    addMessage(redirectAttributes, "保存订单表成功");
		}else{
			addMessage(redirectAttributes, result);
		}
		redirectAttributes.addAttribute("orderCode",order.getOrderCode());
		return "redirect:"+Global.getAdminPath()+"/luxclub/order/?repage";
	}
	
	@RequiresPermissions("luxclub:order:edit")
	@RequestMapping(value = "delete")
	public String delete(Order order, RedirectAttributes redirectAttributes) {
		orderService.delete(order);
		addMessage(redirectAttributes, "删除订单表成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/order/?repage";
	}
}