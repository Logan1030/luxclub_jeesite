package com.footing.website.modules.sys.web;

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
import com.footing.website.common.utils.StringUtils;
import com.footing.website.modules.sys.entity.SerialNumber;
import com.footing.website.modules.sys.service.SerialNumberService;

/**
 * 流水号Controller
 * @author yubin
 * @version 2016-07-23
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/serialNumber")
public class SerialNumberController extends BaseController {

	@Autowired
	private SerialNumberService serialNumberService;
	
	@ModelAttribute
	public SerialNumber get(@RequestParam(required=false) Long id) {
		SerialNumber entity = null;
		if (id != null){
			entity = serialNumberService.get(id);
		}
		if (entity == null){
			entity = new SerialNumber();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:serialNumber:view")
	@RequestMapping(value = {"list", ""})
	public String list(SerialNumber serialNumber, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SerialNumber> page = serialNumberService.findPage(new Page<SerialNumber>(request, response), serialNumber); 
		model.addAttribute("page", page);
		return "modules/sys/serialNumberList";
	}

	@RequiresPermissions("sys:serialNumber:view")
	@RequestMapping(value = "form")
	public String form(SerialNumber serialNumber, Model model) {
		model.addAttribute("serialNumber", serialNumber);
		return "modules/sys/serialNumberForm";
	}

	@RequiresPermissions("sys:serialNumber:edit")
	@RequestMapping(value = "save")
	public String save(SerialNumber serialNumber, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, serialNumber)){
			return form(serialNumber, model);
		}
		serialNumberService.save(serialNumber);
		addMessage(redirectAttributes, "保存流水号成功");
		return "redirect:"+Global.getAdminPath()+"/sys/serialNumber/?repage";
	}
	
	@RequiresPermissions("sys:serialNumber:edit")
	@RequestMapping(value = "delete")
	public String delete(SerialNumber serialNumber, RedirectAttributes redirectAttributes) {
		serialNumberService.delete(serialNumber);
		addMessage(redirectAttributes, "删除流水号成功");
		return "redirect:"+Global.getAdminPath()+"/sys/serialNumber/?repage";
	}

}