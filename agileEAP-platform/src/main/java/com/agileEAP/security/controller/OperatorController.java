package com.agileEAP.security.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.shiro.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agileEAP.data.PageDataResult;
import com.agileEAP.data.RequestPageData;
import com.agileEAP.infrastructure.service.ActionLogService;
import com.agileEAP.security.entity.ObjectRole;
import com.agileEAP.security.entity.Operator;
import com.agileEAP.security.entity.RolePrivilege;
import com.agileEAP.security.service.AccountService;
import com.agileEAP.security.service.OperatorService;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;
import com.agileEAP.utils.*;
/**
 * Operator管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /security/operator/
 * Create page : GET /security/operator/create
 * Create action : POST /security/operator/create
 * Update page : GET /security/operator/update/{id}
 * Update action : POST /security/operator/update
 * Delete action : GET /security/operator/delete/{id}
 * 
 * @author trh
 */
@Controller
@RequestMapping(value = "/security/operator")
public class  OperatorController {

	private static Logger logger = LoggerFactory.getLogger(OperatorController.class);

	@Autowired
	private ActionLogService actionLogService;
	
	@Autowired
	private AccountService accountService;

	@Autowired
	private OperatorService  operatorService;

	@RequestMapping(value = "")
	public String list() {
		return "security/operator/operatorList";
	}

	@RequestMapping(value = "ajaxList")
	@ResponseBody
	public PageDataResult ajaxDataResult(HttpServletRequest request)throws IOException {
		InputStream is = request.getInputStream();
		byte[] bytes = new byte[request.getContentLength()];
		is.read(bytes);
		String json = new String(bytes, request.getCharacterEncoding());

		JsonConvert jsonConvert = new JsonConvert();
		RequestPageData requestData = jsonConvert.fromJson(json,RequestPageData.class);

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("page", requestData.getPage());
		searchParams.put("pageSize", requestData.getPageSize());
		searchParams.putAll(requestData.getData());
		PageDataResult pageDataResult = operatorService.searchByPage(searchParams);

		return pageDataResult;
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String createForm(@RequestParam(required=false) String orgId,@RequestParam(required=false) String orgName,Model model) {
		Operator operator=new Operator ();
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (user != null)
		{
			operator.setCorpID(user.corpId);
			operator.setCorpName(user.corpName);
		}
		operator.setOrgID(orgId);
		operator.setOrgName(orgName);
		model.addAttribute("operator", operator);
		model.addAttribute("action", "add");
		return "security/operator/operatorDetail";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid @ModelAttribute("operator") Operator operator,RedirectAttributes redirectAttributes) {
		String message = "新增系统登录用户表成功";
		try {
			operator.setId(UUID.randomUUID().toString());
			ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			if (user != null)
			{
				operator.setCreator(user.id);
			}
			operator.setUserType((short)1);
			operator.setCreateTime(new Date());
			operator.setExpireTime(new Date());
			operator.setstatus((short)1);

			accountService.register(operator);
			
			JsonConvert jsonConvert = new JsonConvert();
			actionLogService.AddActionLog("新增", message,jsonConvert.toJson(operator));
		} catch (Exception ex) {
			message = "新增用户出错，请检查输入是否正确！";
			logger.error(String.format("新增用户id={0}出错，请检查输入是否正确", operator.getId()),ex);
		}

		return message;
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("operator", operatorService.get(id));
		model.addAttribute("action", "update");
		return "security/operator/operatorDetail";
	}

	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") String  id, Model model) {		
		Operator operator=operatorService.get(id);
		Operator creator=operatorService.get(operator.getCreator());
		if(creator!=null)operator.setCreator(creator.getName());
		
		model.addAttribute("operator", operator);
		model.addAttribute("action", "view");
		return "security/operator/operatorDetail";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute("operator") Operator operator,RedirectAttributes redirectAttributes) {
		String message = "更新成功";
		try {
			accountService.update(operator);			 
			JsonConvert jsonConvert = new JsonConvert();
			actionLogService.AddActionLog("更新", String.format("更新角色id={0}", operator.getId()),jsonConvert.toJson(operator));
		} catch (Exception ex) {
			message = "更新出错，请检查输入是否正确！";
			logger.error(String.format("更新用户id={0}出错，请检查输入是否正确", operator.getId()),ex);
		}

		return message;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam(value = "id") String id){
		String message = "删除成功";		
		try {
			List<String> ids=new ArrayList<String>();
			String[] values=id.split(",");
			if(values!=null)
			{
				for(String fid:values)
				{
					if(fid!=null&&fid.length()>0)
					{
						ids.add(fid);
					}
				}
			}			
			operatorService.batchDelete(ids);
			actionLogService.AddActionLog("删除",String.format("删除用户id={0}", id));
		} catch (Exception ex) {
			message = String.format("删除用户id={0}出错，请检查输入是否正确", id);
			logger.error(message,ex);
		}

		return message;
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出id对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute()
	public void get(@RequestParam(value = "id", required = false) String id,
			Model model) {
		if (id != null && id != "") {
			model.addAttribute("operator", operatorService.get(id));
		} else {
			model.addAttribute("operator", new Operator());
		}
	}

	@RequestMapping(value = "role/save",method = RequestMethod.POST)
	@ResponseBody
	public String saveOperatorRole(@RequestParam String operatorId,@RequestParam String ids){
	String message = "保存成功";
	try {
		
		String[] roleIds=ids.split(",");
        List<ObjectRole> operatorRoles = new ArrayList<>();        
        for(String roleId:roleIds){
        	if(roleId!=null&&roleId.length()>0)
        	{
	        	ObjectRole operatorRole=new ObjectRole();
	        	operatorRole.setId(UUID.randomUUID().toString());
	        	operatorRole.setObjectID(operatorId);
	        	operatorRole.setRoleID(roleId);
	        	operatorRole.setObjectType((short)1);
	        	
	        	operatorRoles.add(operatorRole);
        	}
        }

        operatorService.saveOperatorRole(operatorId, operatorRoles);
        
		JsonConvert jsonConvert = new JsonConvert();
		actionLogService.AddActionLog("保存用户角色，用户id="+operatorId, message,jsonConvert.toJson(operatorRoles));
		
	} catch (Exception ex) {
		message = "保存用户角色出错，请检查输入是否正确！";
		logger.error(String.format("保存用户角色id={0}出错，请检查输入是否正确", operatorId),ex);
	}

	return message;
	}	
}