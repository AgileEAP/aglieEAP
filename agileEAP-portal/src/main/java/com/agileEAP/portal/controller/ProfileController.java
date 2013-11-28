package com.agileEAP.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agileEAP.security.entity.Operator;
import com.agileEAP.security.service.AccountService;
import com.agileEAP.security.service.OperatorService;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;

/**
 * 用户修改自己资料的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private OperatorService operatorService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String updateForm(Model model) {
		String id = getCurrentUserId();
		model.addAttribute("user", accountService.get(id));
		return "security/account/profile";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("preloadUser") Operator operater) {
		accountService.update(operater);
		updateCurrentUserName(operater.getName());
		return "redirect:/";
	}

	@ModelAttribute("preloadUser")
	public Operator getUser(@RequestParam(value = "id", required = false) String id) {
		if (id != null) {
			return accountService.get(id);
		}
		return null;
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private String getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}

	/**
	 * 更新Shiro中当前用户的用户名.
	 */
	private void updateCurrentUserName(String userName) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		user.name = userName;
	}
	
	/**
	 * 获取登录用户对象
	 * 
	 * @return ShiroUser
	 */
	private ShiroUser user() {
		return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
	}
	
	/**
	 * 修改用户密码
	 */
	@RequestMapping(value = "/modifyPwd")
	public String modifyPwdPage(HttpServletRequest request){
		request.setAttribute("id", user().id);
		request.setAttribute("name", user().name);
		request.setAttribute("loginName", user().loginName);
		return "security/account/profile";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String updatePwd(HttpServletRequest request) {
    	String message = "密码修改成功";
    	String id = request.getParameter("id");
    	String password = request.getParameter("password");
    	try {
    		operatorService.updatePwd(id,password);
    	} catch (Exception ex) {
    		message = "密码修改出错，请检查输入是否正确！";
    	}
    	return message;
    }
}
