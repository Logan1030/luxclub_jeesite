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
import com.footing.website.modules.luxclub.common.BusinessConstants;
import com.footing.website.modules.luxclub.common.MemberHisOpreationType;
import com.footing.website.modules.luxclub.common.MemberState;
import com.footing.website.modules.luxclub.entity.MemberInfo;
import com.footing.website.modules.luxclub.entity.MemberInfoHis;
import com.footing.website.modules.luxclub.service.MemberInfoHisService;
import com.footing.website.modules.luxclub.service.MemberInfoService;
import com.footing.website.modules.luxclub.service.MessageNotifyService;
import com.footing.website.modules.luxclub.utils.HttpTookit;

/**
 * 会员信息Controller
 * @author liuguoqing
 * @version 2016-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/luxclub/memberInfo")
public class MemberInfoController extends BaseController {

    @Autowired
    MemberInfoService memberInfoService;

    @Autowired
    MemberInfoHisService memberInfoHisService;
    
    @Autowired
    MessageNotifyService messageNotifyService;
    
    @ModelAttribute
    public MemberInfo get(@RequestParam(required = false) Long id) {
        MemberInfo entity = null;
        if (id != null) {
            entity = memberInfoService.get(id);
        }
        if (entity == null) {
            entity = new MemberInfo();
        }
        return entity;
    }

    @RequiresPermissions("luxclub:memberInfo:view")
    @RequestMapping(value = { "list", "" })
    public String list(MemberInfo memberInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<MemberInfo> page = memberInfoService.findPage(new Page<MemberInfo>(request, response), memberInfo);
        model.addAttribute("page", page);
        return "modules/luxclub/memberInfoList";
    }

    @RequiresPermissions("luxclub:memberInfo:view")
    @RequestMapping(value = "form")
    public String form(MemberInfo memberInfo, Model model) {
        model.addAttribute("memberInfo", memberInfo);
        return "modules/luxclub/memberInfoForm";
    }

    @RequiresPermissions("luxclub:memberInfo:edit")
    @RequestMapping(value = "save")
    public String save(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, memberInfo)) {
            return form(memberInfo, model);
        }
        memberInfoService.save(memberInfo);
        addMessage(redirectAttributes, "保存会员信息成功");
        return "redirect:" + Global.getAdminPath() + "/luxclub/memberInfo/?repage";
    }

    @RequiresPermissions("luxclub:memberInfo:edit")
    @RequestMapping(value = "delete")
    public String delete(MemberInfo memberInfo, RedirectAttributes redirectAttributes) {
        memberInfoService.delete(memberInfo);
        addMessage(redirectAttributes, "删除会员信息成功");
        return "redirect:" + Global.getAdminPath() + "/luxclub/memberInfo/?repage";
    }

    /**
     * 会员卡历史查询
     * @param memberInfoHis
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("luxclub:memberInfo:view")
    @RequestMapping(value = { "hisIndex" })
    public String hisIndex(MemberInfo memberInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        MemberInfoHis memberInfoHis = new MemberInfoHis();
        memberInfoHis.setMemberCardno(memberInfo.getMemberCardno());
        memberInfoHis.setMemberName(memberInfo.getMemberName());
        memberInfoHis.setMemberMobile(memberInfo.getMemberMobile());
        memberInfoHis.setIssuingDate(memberInfo.getIssuingDate());
        memberInfoHis.setExpireDate(memberInfo.getExpireDate());
        Page<MemberInfoHis> page = memberInfoHisService.findPage(new Page<MemberInfoHis>(request, response), memberInfoHis);
        model.addAttribute("page", page);
        model.addAttribute("memberInfoHis", memberInfoHis);
        return "modules/luxclub/memberInfoHisList";
    }
    
    /**
     * 会员卡历史查询
     * @param memberInfoHis
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("luxclub:memberInfo:view")
    @RequestMapping(value = { "hisList" })
    public String hisList(MemberInfoHis memberInfoHis, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<MemberInfoHis> page = memberInfoHisService.findPage(new Page<MemberInfoHis>(request, response), memberInfoHis);
        model.addAttribute("page", page);
        return "modules/luxclub/memberInfoHisList";
    }

    /**
     * 会员卡 激活
     * @param memberInfo
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("luxclub:memberInfo:edit")
    @RequestMapping(value = "doActive")
    public String active(MemberInfo memberInfo, RedirectAttributes redirectAttributes) {
        memberInfo.setState(MemberState.NORMAL);
        memberInfo.setRemarks("人工激活");
        boolean flag = memberInfoService.activeMemberinfo(memberInfo,HttpTookit.getRealIpAddr(currentRequest()));
        if (flag) {
            addMessage(redirectAttributes, "激活会员卡成功");
        } else {
            addMessage(redirectAttributes, "激活会员卡失败");
        }
        return "redirect:" + Global.getAdminPath() + "/luxclub/memberInfo/?repage";
    }

    /**
     * 会员卡 解绑
     * @param memberInfo
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("luxclub:memberInfo:edit")
    @RequestMapping(value = "unbind")
    public String unbindMemberCard(MemberInfo memberInfo, RedirectAttributes redirectAttributes) {
    	memberInfo.setState(MemberState.INACTIVE);
    	memberInfo.preUpdate();
        memberInfoService.updateMemberState(memberInfo);
        boolean flag = memberInfoService.unbindMemberCard(memberInfo);
        if (flag) {
            addMessage(redirectAttributes, "解绑会员卡成功");
        } else {
            addMessage(redirectAttributes, "解绑会员卡失败");
        }
        return "redirect:" + Global.getAdminPath() + "/luxclub/memberInfo/?repage";
    }

    /**
     * 会员卡 冻结
     * @param memberInfo
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("luxclub:memberInfo:edit")
    @RequestMapping(value = "frozen")
    public String frozen(MemberInfo memberInfo, RedirectAttributes redirectAttributes) {
        memberInfo.setState(MemberState.FROZEN);
        boolean flag = memberInfoService.changeMemberCard(memberInfo, MemberHisOpreationType.FROZEN);
        if (flag) {
            addMessage(redirectAttributes, "冻结会员卡成功");
        } else {
            addMessage(redirectAttributes, "冻结会员卡失败");
        }
        return "redirect:" + Global.getAdminPath() + "/luxclub/memberInfo/?repage";
    }

    /**
     * 会员卡 注销
     * @param memberInfo
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("luxclub:memberInfo:edit")
    @RequestMapping(value = "cancel")
    public String cancel(MemberInfo memberInfo, RedirectAttributes redirectAttributes) {
        memberInfo.setState(MemberState.CANCEL);
        boolean flag = memberInfoService.changeMemberCard(memberInfo, MemberHisOpreationType.CANCEL);
        if (flag) {
            addMessage(redirectAttributes, "注销会员卡成功");
        } else {
            addMessage(redirectAttributes, "注销会员卡失败");
        }
        return "redirect:" + Global.getAdminPath() + "/luxclub/memberInfo/?repage";
    }
    /**
     * 
     * <p>
     * Description:激活form页面<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年5月23日
     * @param memberInfo
     * @param model
     * @param redirectAttributes
     * @return
     * String
     */
    @RequiresPermissions("luxclub:memberInfo:view")
    @RequestMapping(value = "active")
    public String rechargeForm(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {
        memberInfo.setRemarks("");
        model.addAttribute("memberInfo", memberInfo);
        return "modules/luxclub/memberActiveForm";
    }

}