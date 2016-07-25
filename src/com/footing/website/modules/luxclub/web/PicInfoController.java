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
import com.footing.website.modules.luxclub.entity.PicInfo;
import com.footing.website.modules.luxclub.service.PicInfoService;

/**
 * 会员图片信息表Controller
 * @author yubin
 * @version 2016-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/luxclub/picInfo")
public class PicInfoController extends BaseController {

	@Autowired
	private PicInfoService picInfoService;
	
	@ModelAttribute
	public PicInfo get(@RequestParam(required=false) Long id) {
		PicInfo entity = null;
		if (id != null){
			entity = picInfoService.get(id);
		}
		if (entity == null){
			entity = new PicInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("luxclub:picInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(PicInfo picInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PicInfo> page = picInfoService.findPage(new Page<PicInfo>(request, response), picInfo); 
		model.addAttribute("page", page);
		return "modules/luxclub/picInfoList";
	}

	@RequiresPermissions("luxclub:picInfo:view")
	@RequestMapping(value = "form")
	public String form(PicInfo picInfo, Model model) {
		model.addAttribute("picInfo", picInfo);
		return "modules/luxclub/picInfoForm";
	}

	@RequiresPermissions("luxclub:picInfo:edit")
	@RequestMapping(value = "save")
	public String save(PicInfo picInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, picInfo)){
			return form(picInfo, model);
		}
		picInfoService.save(picInfo);
		addMessage(redirectAttributes, "保存会员图片信息表成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/picInfo/?repage";
	}
	
	@RequiresPermissions("luxclub:picInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(PicInfo picInfo, RedirectAttributes redirectAttributes) {
		picInfoService.delete(picInfo);
		addMessage(redirectAttributes, "删除会员图片信息表成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/picInfo/?repage";
	}

}