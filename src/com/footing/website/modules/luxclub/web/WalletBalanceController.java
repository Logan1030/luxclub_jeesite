package com.footing.website.modules.luxclub.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.footing.website.modules.luxclub.entity.MemberInfo;
import com.footing.website.modules.luxclub.entity.SiteInfo;
import com.footing.website.modules.luxclub.entity.WalletFeeRecord;
import com.footing.website.modules.luxclub.service.MemberInfoService;
import com.footing.website.modules.luxclub.service.SiteInfoService;
import com.footing.website.modules.luxclub.service.WalletFeeRecordService;

/**
 * 零钱包余额管理
 * @author liuguoqing
 */
@Controller
@RequestMapping(value = "${adminPath}/luxclub/walletBalance")
public class WalletBalanceController extends BaseController {

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private WalletFeeRecordService walletFeeRecordService;
    
    @Autowired
    private SiteInfoService siteInfoService;

    @ModelAttribute
    public MemberInfo get(@RequestParam(required = false) Long id) {
        MemberInfo entity = null;
        if (id != null) {
            entity = memberInfoService.getById(id);
        }
        if (entity == null) {
            entity = new MemberInfo();
        }
        return entity;
    }

    /**
     * 零钱包信息查询
     * @param memberInfo
     * @param request
     * @param response
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("luxclub:walletBalance:view")
    @RequestMapping(value = { "list", "" })
    public String list(MemberInfo memberInfo, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        Page<MemberInfo> page = memberInfoService.findPage(new Page<MemberInfo>(request, response), memberInfo);
        model.addAttribute("page", page);
        return "modules/luxclub/walletBalanceList";
    }

    /**
     * 会员卡历史查询
     * @param walletFeeRecord
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("luxclub:walletBalance:view")
    @RequestMapping(value = { "feeList" })
    public String hisList(WalletFeeRecord walletFeeRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<WalletFeeRecord> page = walletFeeRecordService.findPage(new Page<WalletFeeRecord>(request, response), walletFeeRecord);
        model.addAttribute("page", page);
        List<SiteInfo> list = siteInfoService.findAllList();
        model.addAttribute("siteList", list);
        return "modules/luxclub/walletBalanceFeeList";
    }

    /**
     * 充值form页面
     * 判断会员卡是否为冻结或注销，若为此状态时，提示用户，否则进入充值页面
     * @param memberInfo
     * @param model
     * @return
     */
    @RequiresPermissions("luxclub:walletBalance:view")
    @RequestMapping(value = "rechargeForm")
    public String rechargeForm(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {
        memberInfo.setRemarks("");
        model.addAttribute("memberInfo", memberInfo);
        return "modules/luxclub/walletRechargeForm";
    }

    /**
     * 零钱包 充值
     * @param memberInfo
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("luxclub:walletBalance:edit")
    @RequestMapping(value = "recharge")
    public String recharge(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {
        // 判断会员卡是否为正常状态
        if (StringUtils.isBlank(memberInfo.getVoucherPhoto())) {
            model.addAttribute("message", "请选择充值凭证");
            return "modules/luxclub/walletRechargeForm";
        }
        memberInfoService.walletRecharge(memberInfo);
        super.addMessage(redirectAttributes, "零钱包充值成功");
        // 用于回传到查询页面的卡号
        redirectAttributes.addFlashAttribute("memberCardno", memberInfo.getMemberCardno());
        return "redirect:" + Global.getAdminPath() + "/luxclub/walletBalance/?repage";
    }

    /**
     * 扣费form页面
     * 判断会员卡是否为冻结或注销，若为此状态时，提示用户，否则进入充值页面
     * @param memberInfo
     * @param model
     * @param redirectAttributes
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("luxclub:walletBalance:view")
    @RequestMapping(value = "deductForm")
    public String deductForm(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        memberInfo.setRemarks("");
        model.addAttribute("memberInfo", memberInfo);
        return "modules/luxclub/walletDeductForm";
    }

    /**
     * 零钱包  扣费
     * @param memberInfo
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("luxclub:walletBalance:edit")
    @RequestMapping(value = "deduct")
    public String deduct(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {
        // 扣费金额大于余额，不允许扣费
        if (memberInfo.getAmount().compareTo(memberInfo.getMemberBalance()) == 1) {
            model.addAttribute("message", "扣费金额不能超出零钱包余额！");
            return "modules/luxclub/walletDeductForm";
        }

        if (StringUtils.isBlank(memberInfo.getVoucherPhoto())) {
            model.addAttribute("message", "请选择扣费凭证");
            return "modules/luxclub/walletRechargeForm";
        }

        memberInfoService.walletDeduct(memberInfo);
        addMessage(redirectAttributes, "零钱包扣费成功");
        // 用于回传到查询页面的卡号
        redirectAttributes.addAttribute("memberCardno", memberInfo.getMemberCardno());
        return "redirect:" + Global.getAdminPath() + "/luxclub/walletBalance/?repage";
    }
}
