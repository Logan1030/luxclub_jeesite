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
import com.footing.website.modules.entity.User;
import com.footing.website.modules.luxclub.common.BusinessConstants;
import com.footing.website.modules.luxclub.dao.MemberInfoDao;
import com.footing.website.modules.luxclub.entity.MemberInfo;
import com.footing.website.modules.luxclub.entity.Order;
import com.footing.website.modules.luxclub.entity.SiteInfo;
import com.footing.website.modules.luxclub.service.OrderService;
import com.footing.website.modules.luxclub.service.SiteInfoService;
import com.footing.website.modules.sys.dao.UserDao;

/**
 *  
 * <p>
 * Description:待结账控制层<br />
 * </p>
 * @title AccountOrderController.java
 * @package com.footing.website.modules.luxclub.web 
 * @author yubin
 * @version 0.1 2016年3月17日
 */
@Controller
@RequestMapping(value = "${adminPath}/luxclub/accountorder")
public class AccountOrderController extends BaseController {
    
	private static final Logger log=Logger.getLogger(AccountOrderController.class);
	@Autowired
	private OrderService orderService;
	@Autowired
	private MemberInfoDao memberInfoDao;
	@Autowired
	private SiteInfoService siteInfoService;
	@Autowired
	private UserDao userdao;
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
	
	@RequiresPermissions("luxclub:accountorder:view")
	@RequestMapping(value = {"list", ""})
	public String list(Order order, HttpServletRequest request, HttpServletResponse response, Model model) {
		order.setState("5");
		Page<Order> page = orderService.findPage(new Page<Order>(request, response), order); 
		model.addAttribute("page", page);
		return "modules/luxclub/accountorderList";
	}

	@RequiresPermissions("luxclub:accountorder:view")
	@RequestMapping(value = "form")
	public String form(Order order, Model model) {
		MemberInfo memberInfo=memberInfoDao.queryMemberInfoByCardno(order.getMemberCardno(),null);
	    User user=userdao.get(order.getBusinessId());
		model.addAttribute("order", order);
		model.addAttribute("user", user);
		model.addAttribute("memberInfo", memberInfo);
		List<SiteInfo> list = siteInfoService.findAllList();
		model.addAttribute("siteList", list);
		return "modules/luxclub/accountorderForm";
	}

	@RequiresPermissions("luxclub:accountorder:edit")
	@RequestMapping(value = "save")
	public String save(Order order, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, order)){
			return form(order, model);
		}
		String result=BusinessConstants.SUCCESS;
		try {
			result=orderService.accountOrder(order);
		} catch (Exception e) {
			log.info("结账业务异常：", e);
			result="出现异常：请稍后处理";
		}  
		addMessage(redirectAttributes, result);
		return "redirect:"+Global.getAdminPath()+"/luxclub/accountorder/?repage";
	}
	
	@RequiresPermissions("luxclub:accountorder:edit")
	@RequestMapping(value = "delete")
	public String delete(Order order, RedirectAttributes redirectAttributes) {
		orderService.delete(order);
		addMessage(redirectAttributes, "删除订单表成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/accountorder/?repage";
	}
}