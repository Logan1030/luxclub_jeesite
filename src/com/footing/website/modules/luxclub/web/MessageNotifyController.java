package com.footing.website.modules.luxclub.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.footing.website.modules.entity.User;
import com.footing.website.modules.luxclub.entity.MessageNotify;
import com.footing.website.modules.luxclub.service.MessageNotifyService;
import com.footing.website.modules.sys.utils.UserUtils;

/**
 * 消息通知Controller
 * @author liuguoqing
 * @version 2016-03-15
 */
@Controller
@RequestMapping(value = "${adminPath}/luxclub/messageNotify")
public class MessageNotifyController extends BaseController {

	@Autowired
	private MessageNotifyService messageNotifyService;
	
	@ModelAttribute
	public MessageNotify get(@RequestParam(required=false) Long id) {
		MessageNotify entity = null;
		if (id != null){
			entity = messageNotifyService.get(id);
		}
		if (entity == null){
			entity = new MessageNotify();
		}
		return entity;
	}
	
	@RequiresPermissions("luxclub:messageNotify:view")
	@RequestMapping(value = {"list", ""})
	public String list(MessageNotify messageNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MessageNotify> page = messageNotifyService.findPage(new Page<MessageNotify>(request, response), messageNotify);
		for(MessageNotify msgNotify :page.getList()){
		    String receiver = msgNotify.getReceiver();
		    if(StringUtils.isNotBlank(receiver) && StringUtils.isNumeric(receiver)){
		        User user = UserUtils.get(Long.parseLong(receiver));
		        if(user != null){
		            msgNotify.setReceiverName(user.getName());
		        }
		    }else{
		        msgNotify.setReceiverName(receiver);
		    }
		}
		model.addAttribute("page", page);
		return "modules/luxclub/messageNotifyList";
	}

	@RequiresPermissions("luxclub:messageNotify:view")
	@RequestMapping(value = "form")
	public String form(MessageNotify messageNotify, Model model) {
		model.addAttribute("messageNotify", messageNotify);
		return "modules/luxclub/messageNotifyForm";
	}

	@RequiresPermissions("luxclub:messageNotify:edit")
	@RequestMapping(value = "save")
	public String save(MessageNotify messageNotify, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, messageNotify)){
			return form(messageNotify, model);
		}
		messageNotifyService.save(messageNotify);
		addMessage(redirectAttributes, "保存消息通知成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/messageNotify/?repage";
	}
	
	@RequiresPermissions("luxclub:messageNotify:edit")
	@RequestMapping(value = "delete")
	public String delete(MessageNotify messageNotify, RedirectAttributes redirectAttributes) {
		messageNotifyService.delete(messageNotify);
		addMessage(redirectAttributes, "删除消息通知成功");
		return "redirect:"+Global.getAdminPath()+"/luxclub/messageNotify/?repage";
	}

}