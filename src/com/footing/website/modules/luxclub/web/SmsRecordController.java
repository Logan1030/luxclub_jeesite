package com.footing.website.modules.luxclub.web;

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
import com.footing.website.modules.luxclub.entity.SmsRecord;
import com.footing.website.modules.luxclub.service.SmsRecordService;

/**
 * 短信Controller
 * @author yubin
 * @version 2016-07-21
 */
@Controller
@RequestMapping(value = "${adminPath}/luxclub/smsRecord")
public class SmsRecordController extends BaseController {

	@Autowired
	private SmsRecordService smsRecordService;
	
	@ModelAttribute
	public SmsRecord get(@RequestParam(required=false) Long id) {
		SmsRecord entity = null;
		if (id != null){
			entity = smsRecordService.get(id);
		}
		if (entity == null){
			entity = new SmsRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("luxclub:smsRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmsRecord smsRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsRecord> page = smsRecordService.findPage(new Page<SmsRecord>(request, response), smsRecord); 
		model.addAttribute("page", page);
		return "modules/luxclub/smsRecordList";
	}

	@RequiresPermissions("luxclub:smsRecord:view")
	@RequestMapping(value = "form")
	public String form(SmsRecord smsRecord, Model model) {
		model.addAttribute("smsRecord", smsRecord);
		return "modules/luxclub/smsRecordForm";
	}

	@RequiresPermissions("luxclub:smsRecord:edit")
	@RequestMapping(value = "save")
	public String save(SmsRecord smsRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsRecord)){
			return form(smsRecord, model);
		}
		smsRecordService.save(smsRecord);
		addMessage(redirectAttributes, "保存短信成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/smsRecord/?repage";
	}
	
	@RequiresPermissions("luxclub:smsRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsRecord smsRecord, RedirectAttributes redirectAttributes) {
		smsRecordService.delete(smsRecord);
		addMessage(redirectAttributes, "删除短信成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/smsRecord/?repage";
	}

}