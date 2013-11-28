package com.agileEAP.security.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import javax.security.auth.kerberos.KerberosKey;
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
import com.agileEAP.data.TreeNode;
import com.agileEAP.infrastructure.service.ActionLogService;
import com.agileEAP.infrastructure.service.SysParamService;
import com.agileEAP.security.entity.Resource;
import com.agileEAP.security.entity.Role;
import com.agileEAP.security.entity.RolePrivilege;
import com.agileEAP.security.service.ResourceService;
import com.agileEAP.security.service.RoleService;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;
import com.agileEAP.utils.*;
import com.agileEAP.web.Servlets;


/**
 * Role管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /security/role/ Create page : GET /security/role/create
 * Create action : POST /security/role/create Update page : GET
 * /security/role/update/{id} Update action : POST /security/role/update Delete
 * action : GET /security/role/delete/{id}
 * 
 * @author trh
 */
@Controller
@RequestMapping(value = "/security/role")
public class RoleController {

	private static Logger logger = LoggerFactory
			.getLogger(RoleController.class);

	@Autowired
	private ActionLogService actionLogService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private SysParamService sysParamService;

	@RequestMapping(value = "")
	public String list(@RequestParam(value="operatorId",required=false) String operatorId,Model model) {
		if(operatorId!=null&&operatorId.length()>0)
		{
			List<String> rolesList=	roleService.getOperatorRoles(operatorId);			
			model.addAttribute("roles",Collections3.convertToString(rolesList,","));
		}
		
		return "security/role/roleList";
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
		//int index = (requestData.getPage() - 1) * requestData.getPageSize();
		searchParams.put("page", requestData.getPage());
		searchParams.put("pageSize", requestData.getPageSize());
		searchParams.putAll(requestData.getData());
		PageDataResult pageDataResult = roleService.searchByPage(searchParams);

		return pageDataResult;
	}
	
	@RequestMapping(value = "privilege",method = RequestMethod.GET)
	public String privilege(@RequestParam String roleId,Model model) {
		model.addAttribute("id", roleId);
		return "security/role/rolePrivilege";
	}
	
	@RequestMapping(value = "privilege",method = RequestMethod.POST)
	@ResponseBody
	public List<TreeNode> privilege(@RequestParam String roleId){
		List<String> authorizedPrivileges=roleService.getRolePrivileges(roleId);		
		List<HashMap<String, String>> privleges = resourceService.getPrivileges();
		
		String sysID=sysParamService.getSysParamValue("SysID");
		String sysName=sysParamService.getSysParamValue("SysName");
		TreeNode node = new TreeNode();
		node.setId(sysID);
		node.setText(sysName);
		node.setExpanded(true);
		node.setSpriteCssClass("");
		node.setChecked(authorizedPrivileges.contains(sysID));
		
		buildTreeNode(node,sysID,privleges,authorizedPrivileges);
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		nodes.add(node);
		
		return nodes;
	}	
		
	@RequestMapping(value = "privilege/save",method = RequestMethod.POST)
	@ResponseBody
	public String saveRolePrivilege(@RequestParam String roleId,@RequestParam String ids){
	String message = "保存成功";
	try {
        List<RolePrivilege> rolePrivileges = new ArrayList<>();   
		String[] privileges=ids.split(",");     
        for(String privilegeID:privileges){
        	if(privilegeID!=null&&privilegeID.length()>0)
        	{
	        	RolePrivilege rolePrivilege=new RolePrivilege();
	        	rolePrivilege.setId(UUID.randomUUID().toString());
	        	rolePrivilege.setPrivilegeID(privilegeID);
	        	rolePrivilege.setRoleID(roleId);
	        	rolePrivileges.add(rolePrivilege);
        	}
        }

		roleService.saveRolePrivilege(roleId,rolePrivileges);
		JsonConvert jsonConvert = new JsonConvert();
		actionLogService.AddActionLog("保存角色权限id="+roleId, message,jsonConvert.toJson(rolePrivileges));
		
	} catch (Exception ex) {
		message = "保存角色权限出错，请检查输入是否正确！";
		logger.error(String.format("保存角色权限id={0}出错，请检查输入是否正确", roleId),ex);
	}

	return message;
	}	

	private void buildTreeNode(TreeNode parentNode,String parentID,List<HashMap<String, String>> privleges,List<String> authorizedPrivileges) {

		for(HashMap<String, String> map : privleges)
		{
			if (parentID.equals(map.get("PARENTID"))) {
				TreeNode node = new TreeNode();
				node.setId(map.get("PRIVILEGEID"));
				node.setText(map.get("NAME"));
				node.setExpanded(true);
				node.setSpriteCssClass("");
				node.setChecked(authorizedPrivileges.contains(map.get("PRIVILEGEID")));
				// node.setItems(new ArrayList<TreeNode>());

				if (parentNode.getItems() == null) {
					parentNode.setItems(new ArrayList<TreeNode>());
					parentNode.setHasChildren(true);
				}
				parentNode.getItems().add(node);

				buildTreeNode(node,map.get("ID"),privleges,authorizedPrivileges);
			}			
		}		
	}
	

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("role", new Role());
		model.addAttribute("action", "add");
		return "security/role/roleDetail";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid @ModelAttribute("role") Role role,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String message = "新增角色成功";
		try {
			role.setId(UUID.randomUUID().toString());
			ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			if (user != null)
			{
				role.setCreator(user.name);
			}

			role.setCreateTime(new Date());
			role.setOrgID(user.orgId);

			roleService.save(role);
			JsonConvert jsonConvert = new JsonConvert();
			actionLogService.AddActionLog(Servlets.getClientIp(request), "新增", message,jsonConvert.toJson(role));
		} catch (Exception ex) {
			message = "新增角色出错，请检查输入是否正确！";
			logger.error(String.format("新增角色id={0}出错，请检查输入是否正确", role.getId()),ex);
		}

		return message;
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("role", roleService.get(id));
		model.addAttribute("action", "update");
		return "security/role/roleDetail";
	}

	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") String id, Model model) {
		model.addAttribute("role", roleService.get(id));
		model.addAttribute("action", "view");
		return "security/role/roleDetail";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute("role") Role role,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String message = "更新成功";
		try {

			ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			role.setOrgID(user.orgId);
			roleService.update(role);
			JsonConvert jsonConvert = new JsonConvert();
			actionLogService.AddActionLog(Servlets.getClientIp(request), "更新", String.format("更新角色id={0}", role.getId()),jsonConvert.toJson(role));
		} catch (Exception ex) {
			message = "更新出错，请检查输入是否正确！";
			logger.error(String.format("更新角色id={0}出错，请检查输入是否正确", role.getId()),ex);
		}

		return message;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam(value = "id") String id,HttpServletRequest request ){
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
			roleService.batchDelete(ids);
			actionLogService.AddActionLog(Servlets.getClientIp(request),"删除",String.format("删除角色id={0}", id),id);
		} catch (Exception ex) {
			message = String.format("删除角色id={0}出错，请检查输入是否正确", id);
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
			model.addAttribute("role", roleService.get(id));
		} else {
			model.addAttribute("role", new Role());
		}
	}
}
