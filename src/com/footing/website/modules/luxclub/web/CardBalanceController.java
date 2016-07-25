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
import com.footing.website.modules.luxclub.common.BusinessConstants;
import com.footing.website.modules.luxclub.entity.FeeRecord;
import com.footing.website.modules.luxclub.entity.MemberInfo;
import com.footing.website.modules.luxclub.entity.Order;
import com.footing.website.modules.luxclub.entity.SiteInfo;
import com.footing.website.modules.luxclub.service.FeeRecordService;
import com.footing.website.modules.luxclub.service.MemberInfoService;
import com.footing.website.modules.luxclub.service.MessageNotifyService;
import com.footing.website.modules.luxclub.service.OrderService;
import com.footing.website.modules.luxclub.service.SiteInfoService;

/**
 * 
 * @author 会员卡余额管理
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/luxclub/cardBalance")
public class CardBalanceController extends BaseController {

    @Autowired
    private MemberInfoService memberInfoService;
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private FeeRecordService feeRecordService;
    
    @Autowired
    private SiteInfoService siteInfoService;
    @Autowired
    private MessageNotifyService messageNotifyService;
    @ModelAttribute
    public MemberInfo get(@RequestParam(required=false) Long id) {
        MemberInfo entity = null;
        if (id != null){
            entity = memberInfoService.getById(id);
        }
        if (entity == null){
            entity = new MemberInfo();
        }
        return entity;
    }

    @RequiresPermissions("luxclub:cardBalance:view")
    @RequestMapping(value = {"list", ""})
    public String list(MemberInfo memberInfo, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        Page<MemberInfo> page = memberInfoService.findPage(new Page<MemberInfo>(request, response), memberInfo); 
        model.addAttribute("page", page);
        return "modules/luxclub/cardBalanceList";
    }
    
    /**
     * 会员卡费用查询
     * @param feeRecord
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("luxclub:cardBalance:view")
    @RequestMapping(value = { "feeList" })
    public String hisList(FeeRecord feeRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<FeeRecord> page = feeRecordService.findPage(new Page<FeeRecord>(request, response), feeRecord);
        model.addAttribute("page", page);
        List<SiteInfo> list = siteInfoService.findAllList();
        model.addAttribute("siteList", list);
        return "modules/luxclub/cardBalanceFeeList";
    }
    
    /**
     * 充值form页面
     * 判断会员卡是否为冻结或注销，若为此状态时，提示用户，否则进入充值页面
     * @param memberInfo
     * @param model
     * @return
     */
    @RequiresPermissions("luxclub:cardBalance:view")
    @RequestMapping(value = "rechargeForm")
    public String rechargeForm(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {
        memberInfo.setRemarks("");
        model.addAttribute("memberInfo", memberInfo);
        return "modules/luxclub/memberRechargeForm";
    }
    
    /**
     * 会员卡  充值
     * @param memberInfo
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("luxclub:cardBalance:edit")
    @RequestMapping(value = "recharge")
    public String recharge(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {
        // 判断会员卡是否为正常状态
        if(StringUtils.isBlank(memberInfo.getVoucherPhoto())){
            model.addAttribute("message", "请选择充值凭证");
            return "modules/luxclub/memberRechargeForm";
        }
        memberInfoService.balanceRecharge(memberInfo);
        memberInfo=memberInfoService.get(memberInfo.getId());
        messageNotifyService.sendSmsMessage(memberInfo, BusinessConstants.SMS_CHARGE_CARD, memberInfo.getMemberCardno());
        super.addMessage(redirectAttributes, "会员卡充值成功");
        //用于回传到查询页面的卡号
        redirectAttributes.addFlashAttribute("memberCardno", memberInfo.getMemberCardno());
        return "redirect:"+Global.getAdminPath()+"/luxclub/cardBalance/?repage";
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
    @RequiresPermissions("luxclub:cardBalance:view")
    @RequestMapping(value = "deductForm")
    public String deductForm(MemberInfo memberInfo, Model model,RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        memberInfo.setRemarks("");
        model.addAttribute("memberInfo", memberInfo);
        //查询此卡号的订单
        Order order = new Order();
        order.setMemberCardno(memberInfo.getMemberCardno());
        //order.setState("5");//FIXME 待结账状态
        Page<Order> page = orderService.findPage(new Page<Order>(request, response), order); 
        model.addAttribute("page", page);
        return "modules/luxclub/memberDeductForm";
    }
    
    /**
     * 会员卡  扣费
     * @param memberInfo
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("luxclub:cardBalance:edit")
    @RequestMapping(value = "deduct")
    public String deduct(MemberInfo memberInfo, Model model, RedirectAttributes redirectAttributes) {
        //扣费金额大于余额，不允许扣费
        if(memberInfo.getAmount().compareTo(memberInfo.getMemberBalance())==1){
            model.addAttribute("message", "扣费金额不能超出会员卡余额！");
            return "modules/luxclub/memberDeductForm";
        }
        
        if(StringUtils.isBlank(memberInfo.getVoucherPhoto())){
            model.addAttribute("message", "请选择扣费凭证");
            return "modules/luxclub/memberRechargeForm";
        }
        
        memberInfoService.balanceDeduct(memberInfo);
        addMessage(redirectAttributes, "会员卡扣费成功");
        //用于回传到查询页面的卡号
        redirectAttributes.addAttribute("memberCardno", memberInfo.getMemberCardno());
        return "redirect:"+Global.getAdminPath()+"/luxclub/cardBalance/?repage";
    }
}
