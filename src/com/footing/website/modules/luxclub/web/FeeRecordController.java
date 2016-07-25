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
import com.footing.website.modules.luxclub.entity.FeeRecord;
import com.footing.website.modules.luxclub.entity.SiteInfo;
import com.footing.website.modules.luxclub.service.FeeRecordService;
import com.footing.website.modules.luxclub.service.SiteInfoService;

/**
 * 费用记录Controller
 * @author liuguoqing
 * @version 2016-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/luxclub/feeRecord")
public class FeeRecordController extends BaseController {

	@Autowired
	private FeeRecordService feeRecordService;
	
	@Autowired
	private SiteInfoService siteInfoService;
	
	@ModelAttribute
	public FeeRecord get(@RequestParam(required=false) Long id) {
		FeeRecord entity = null;
		if (id != null){
			entity = feeRecordService.get(id);
		}
		if (entity == null){
			entity = new FeeRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("luxclub:feeRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(FeeRecord feeRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FeeRecord> page = feeRecordService.findPage(new Page<FeeRecord>(request, response), feeRecord); 
		model.addAttribute("page", page);
		List<SiteInfo> list = siteInfoService.findAllList();
		model.addAttribute("siteList", list);
		return "modules/luxclub/feeRecordList";
	}

	@RequiresPermissions("luxclub:feeRecord:view")
	@RequestMapping(value = "form")
	public String form(FeeRecord feeRecord, Model model) {
		model.addAttribute("feeRecord", feeRecord);
		return "modules/luxclub/feeRecordForm";
	}

	@RequiresPermissions("luxclub:feeRecord:edit")
	@RequestMapping(value = "save")
	public String save(FeeRecord feeRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, feeRecord)){
			return form(feeRecord, model);
		}
		feeRecordService.save(feeRecord);
		addMessage(redirectAttributes, "保存费用记录成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/feeRecord/?repage";
	}
	
	@RequiresPermissions("luxclub:feeRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(FeeRecord feeRecord, RedirectAttributes redirectAttributes) {
		feeRecordService.delete(feeRecord);
		addMessage(redirectAttributes, "删除费用记录成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/feeRecord/?repage";
	}

}