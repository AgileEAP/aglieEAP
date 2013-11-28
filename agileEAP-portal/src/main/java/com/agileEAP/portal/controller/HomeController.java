package com.agileEAP.portal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.agileEAP.data.MenuItem;
import com.agileEAP.infrastructure.service.SysParamService;
import com.agileEAP.security.entity.Resource;
import com.agileEAP.security.service.ResourceService;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;

@Controller
@RequestMapping(value = "/")
public class HomeController {
	
	@Autowired
	private ResourceService resourceService;	
	
	@Autowired
	private SysParamService sysParamService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpServletRequest request,Model model) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("currentUser", user.loginName);
		
		//得到第一级目录
		String sysID=sysParamService.getSysParamValue("SysID");
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("parentID",sysID);
		searchParams.put("$orderby", "sortOrder");
		List<Resource> headerMenus = resourceService.search(searchParams);

		List<String> authoriedResources = resourceService.getAuthorizedResources(user.id);
		for (Resource resource:headerMenus) {
			resource.setAuthorized(user.userType==0||authoriedResources.contains(resource.getId()));
		}
		
		request.setAttribute("headerMenus", headerMenus);
		
		return "home/default";
	}
	
	@RequestMapping(value = "home/main",method = RequestMethod.GET)
	public String main() {
		return "home/main";
	}
	
	private void buildMenuItem(MenuItem parentMenuItem, String parentID,List<String> authoriedResources) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("parentID", parentID);
		searchParams.put("$orderby", "sortOrder");
		List<Resource> resources = resourceService.search(searchParams);
		
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();	
		
		for (Resource resource : resources) {			
			if(user.userType==0||authoriedResources.contains(resource.getId()))
			{
				MenuItem menuItem = new MenuItem();
				menuItem.setId(resource.getId());
				menuItem.setName(resource.getName());
				menuItem.setExpanded(false);
				menuItem.setUrl(resource.getUrl());		
				menuItem.setIcon(resource.getIcon());
				if (parentMenuItem.getChildren() == null) {
					parentMenuItem.setChildren(new ArrayList<MenuItem>());
				}
				parentMenuItem.getChildren().add(menuItem);
	
				buildMenuItem(menuItem, resource.getId(),authoriedResources);
			}
		}
	}
	
	@RequestMapping(value = "home/menu",method = RequestMethod.GET)
	public String menu(HttpServletRequest request) {
		String id = request.getParameter("id");
		if(id==null||id.length()==0)
		{
			String sysID=sysParamService.getSysParamValue("SysID");
			id =resourceService.getDefaultMenu(sysID);
		}
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("parentID", id);
		searchParams.put("$orderby", "sortOrder");
		List<Resource> resources = resourceService.search(searchParams);

		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();		
		List<String> authoriedResources = resourceService.getAuthorizedResources(user.id);
		
		List<MenuItem> menuItems = new ArrayList<MenuItem>();
		for (int i=0;i<resources.size();i++) {
			Resource resource=resources.get(i);
			if(user.userType==0||authoriedResources.contains(resource.getId()))
			{				
				MenuItem menuItem = new MenuItem();
				menuItem.setId(resource.getId());
				menuItem.setName(resource.getName());
				menuItem.setExpanded(i==0);
				menuItem.setUrl(resource.getUrl());	
				menuItem.setIcon(resource.getIcon());
				menuItem.setAuthorized(authoriedResources.contains(resource.getId()));
	
				menuItems.add(menuItem);
				buildMenuItem(menuItem,resource.getId(),authoriedResources);
			}
		}
		
		request.setAttribute("menuItems", menuItems);
		
		return "home/menu";
	}
}
