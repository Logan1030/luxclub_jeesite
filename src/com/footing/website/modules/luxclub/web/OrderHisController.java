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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.footing.website.common.config.Global;
import com.footing.website.common.persistence.Page;
import com.footing.website.common.web.BaseController;
import com.footing.website.modules.luxclub.entity.OrderHis;
import com.footing.website.modules.luxclub.entity.SiteInfo;
import com.footing.website.modules.luxclub.service.OrderHisService;
import com.footing.website.modules.luxclub.service.SiteInfoService;

/**
 * 订单历史表Controller
 * @author yubin
 * @version 2016-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/luxclub/orderHis")
public class OrderHisController extends BaseController {

	@Autowired
	private OrderHisService orderHisService;
	@Autowired
	private SiteInfoService siteInfoService;
	@ModelAttribute
	public OrderHis get(@RequestParam(required=false) Long id) {
		OrderHis entity = null;
		if (id != null){
			entity = orderHisService.get(id);
		}
		if (entity == null){
			entity = new OrderHis();
		}
		return entity;
	}
	
	@RequiresPermissions("luxclub:orderHis:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderHis orderHis, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderHis> page = orderHisService.findPage(new Page<OrderHis>(request, response), orderHis); 
		model.addAttribute("page", page);
		return "modules/luxclub/orderHisList";
	}

	@RequiresPermissions("luxclub:orderHis:view")
	@RequestMapping(value = "form")
	public String form(OrderHis orderHis, Model model) {
		model.addAttribute("orderHis", orderHis);
		List<SiteInfo> list = siteInfoService.findAllList();
		model.addAttribute("siteList", list);
		return "modules/luxclub/orderHisForm";
	}

	@RequiresPermissions("luxclub:orderHis:edit")
	@RequestMapping(value = "save")
	public String save(OrderHis orderHis, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderHis)){
			return form(orderHis, model);
		}
		orderHisService.save(orderHis);
		addMessage(redirectAttributes, "保存订单历史表成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/orderHis/?repage";
	}
	
	@RequiresPermissions("luxclub:orderHis:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderHis orderHis, RedirectAttributes redirectAttributes) {
		orderHisService.delete(orderHis);
		addMessage(redirectAttributes, "删除订单历史表成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/orderHis/?repage";
	}

}