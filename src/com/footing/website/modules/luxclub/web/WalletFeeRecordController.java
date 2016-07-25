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
import com.footing.website.modules.luxclub.entity.SiteInfo;
import com.footing.website.modules.luxclub.entity.WalletFeeRecord;
import com.footing.website.modules.luxclub.service.SiteInfoService;
import com.footing.website.modules.luxclub.service.WalletFeeRecordService;

/**
 * 零钱包费用记录Controller
 * @author liuguoqing
 */
@Controller
@RequestMapping(value = "${adminPath}/luxclub/walletFeeRecord")
public class WalletFeeRecordController extends BaseController {

	@Autowired
	private WalletFeeRecordService walletFeeRecordService;
	
	@Autowired
	private SiteInfoService siteInfoService;

	@ModelAttribute
	public WalletFeeRecord get(@RequestParam(required=false) Long id) {
	    WalletFeeRecord entity = null;
		if (id != null){
			entity = walletFeeRecordService.get(id);
		}
		if (entity == null){
			entity = new WalletFeeRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("luxclub:walletFeeRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(WalletFeeRecord walletFeeRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WalletFeeRecord> page = walletFeeRecordService.findPage(new Page<WalletFeeRecord>(request, response), walletFeeRecord); 
		model.addAttribute("page", page);
		List<SiteInfo> list = siteInfoService.findAllList();
		model.addAttribute("siteList", list);
		return "modules/luxclub/walletFeeRecordList";
	}

	@RequiresPermissions("luxclub:walletFeeRecord:view")
	@RequestMapping(value = "form")
	public String form(WalletFeeRecord walletFeeRecord, Model model) {
		model.addAttribute("walletFeeRecord", walletFeeRecord);
		return "modules/luxclub/walletFeeRecordForm";
	}

	@RequiresPermissions("luxclub:walletFeeRecord:edit")
	@RequestMapping(value = "save")
	public String save(WalletFeeRecord walletFeeRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, walletFeeRecord)){
			return form(walletFeeRecord, model);
		}
		walletFeeRecordService.save(walletFeeRecord);
		addMessage(redirectAttributes, "保存零钱包费用记录成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/walletFeeRecord/?repage";
	}
	
	@RequiresPermissions("luxclub:walletFeeRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(WalletFeeRecord walletFeeRecord, RedirectAttributes redirectAttributes) {
		walletFeeRecordService.delete(walletFeeRecord);
		addMessage(redirectAttributes, "删除零钱包费用记录成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/walletFeeRecord/?repage";
	}

}