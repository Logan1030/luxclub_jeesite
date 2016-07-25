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
import com.footing.website.modules.luxclub.entity.SiteInfo;
import com.footing.website.modules.luxclub.service.SiteInfoService;
import com.footing.website.modules.sys.service.AreaService;

/**
 * 场所信息Controller
 * @author yubin
 * @version 2016-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/luxclub/siteInfo")
public class SiteInfoController extends BaseController {

	@Autowired
	private SiteInfoService siteInfoService;
	
	@Autowired
	private AreaService areaService;
	
	@ModelAttribute
	public SiteInfo get(@RequestParam(required=false) Long id) {
		SiteInfo entity = null;
		if (id != null){
			entity = siteInfoService.get(id);
		}
		if (entity == null){
			entity = new SiteInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("luxclub:siteInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(SiteInfo siteInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SiteInfo> page = siteInfoService.findPage(new Page<SiteInfo>(request, response), siteInfo); 
		model.addAttribute("page", page);
		return "modules/luxclub/siteInfoList";
	}

	@RequiresPermissions("luxclub:siteInfo:view")
	@RequestMapping(value = "form")
	public String form(SiteInfo siteInfo, Model model) {
		if(null != siteInfo){
		   if(siteInfo.getSiteAreaId()==null){
			siteInfo.setArea(areaService.findAll().get(0));
   		   }else{
		    siteInfo.setArea(areaService.get(siteInfo.getSiteAreaId()));
   		   }
		} 
		model.addAttribute("siteInfo", siteInfo);
		return "modules/luxclub/siteInfoForm";
	}

	@RequiresPermissions("luxclub:siteInfo:edit")
	@RequestMapping(value = "save")
	public String save(SiteInfo siteInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, siteInfo)){
			return form(siteInfo, model);
		}
		
		siteInfoService.saveSiteAndPic(siteInfo);;
		addMessage(redirectAttributes, "保存场所信息成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/siteInfo/?repage";
	}
	/**
     * 会员卡 冻结
     * @param memberInfo
     * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("luxclub:siteInfo:edit")
    @RequestMapping(value = "stick")
    public String frozen(SiteInfo siteInfo, RedirectAttributes redirectAttributes) {
        try {
			siteInfoService.stickSiteInfo(siteInfo.getId());
		} catch (Exception e) {
			addMessage(redirectAttributes, "置顶失败");
			logger.error("置顶异常", e);
		} 
        addMessage(redirectAttributes, "置顶成功");
        return "redirect:" + Global.getAdminPath() + "/luxclub/siteInfo/?repage";
    }
	
	@RequiresPermissions("luxclub:siteInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(SiteInfo siteInfo, RedirectAttributes redirectAttributes) {
		siteInfoService.delete(siteInfo);
		addMessage(redirectAttributes, "删除场所信息成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/siteInfo/?repage";
	}
	
	 

}