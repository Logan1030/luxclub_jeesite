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
import com.footing.website.common.utils.DateUtils;
import com.footing.website.common.utils.excel.ExportExcel;
import com.footing.website.common.web.BaseController;
import com.footing.website.modules.luxclub.entity.CardBatch;
import com.footing.website.modules.luxclub.entity.MemberInfo;
import com.footing.website.modules.luxclub.service.CardBatchService;
import com.footing.website.modules.luxclub.service.MemberInfoService;

/**
 * 会员卡批次生成Controller
 * @author liuguoqing
 * @version 2016-03-15
 */
@Controller
@RequestMapping(value = "${adminPath}/luxclub/cardBatch")
public class CardBatchController extends BaseController {

	@Autowired
	private CardBatchService cardBatchService;

	@Autowired
	private MemberInfoService memberInfoService;
	
	@ModelAttribute
	public CardBatch get(@RequestParam(required=false) Long id) {
		CardBatch entity = null;
		if (id != null){
			entity = cardBatchService.get(id);
		}
		if (entity == null){
			entity = new CardBatch();
		}
		return entity;
	}
	
	@RequiresPermissions("luxclub:cardBatch:view")
	@RequestMapping(value = {"list", ""})
	public String list(CardBatch cardBatch, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CardBatch> page = cardBatchService.findPage(new Page<CardBatch>(request, response), cardBatch); 
		model.addAttribute("page", page);
		return "modules/luxclub/cardBatchList";
	}

	@RequiresPermissions("luxclub:cardBatch:view")
	@RequestMapping(value = "form")
	public String form(CardBatch cardBatch, Model model) {
		model.addAttribute("cardBatch", cardBatch);
		return "modules/luxclub/cardBatchForm";
	}

	/**
	 * 保存
	 * @param cardBatch
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("luxclub:cardBatch:edit")
	@RequestMapping(value = "save")
	public String save(CardBatch cardBatch, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cardBatch)){
			return form(cardBatch, model);
		}
		cardBatchService.save(cardBatch);
		addMessage(redirectAttributes, "保存批次信息成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/cardBatch/?repage";
	}
	
	/**
	 * 批量生成会员卡数据
	 * @param cardBatch
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("luxclub:cardBatch:edit")
	@RequestMapping(value = "batchGenerate")
	public String batchGenerate(CardBatch cardBatch, Model model, RedirectAttributes redirectAttributes) {
	    if (!beanValidator(model, cardBatch)){
	        return form(cardBatch, model);
	    }
	    cardBatchService.batchGenerate(cardBatch);
	    addMessage(redirectAttributes, "保存会员卡批次成功");
	    return "redirect:"+Global.getAdminPath()+"/luxclub/cardBatch/?repage";
	}
	
	/**
	 * 删除
	 * @param cardBatch
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("luxclub:cardBatch:edit")
	@RequestMapping(value = "delete")
	public String delete(CardBatch cardBatch, RedirectAttributes redirectAttributes) {
		cardBatchService.delete(cardBatch);
		addMessage(redirectAttributes, "删除会员卡批次信息成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/cardBatch/?repage";
	}
	
	/**
     * 导出会员卡数据
     * @param id 批次ID
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("luxclub:cardBatch:edit")
    @RequestMapping(value = "export")
    public String exportFile(Long id, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "会员卡数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<MemberInfo> resultList = memberInfoService.findListByBatchId(id);
            new ExportExcel("会员卡数据", MemberInfo.class).setDataList(resultList).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出会员卡失败！失败信息："+e.getMessage());
            logger.error(e.getMessage(),e);
        }
        return "redirect:" + adminPath + "/luxclub/cardBatch/?repage";
    }

}