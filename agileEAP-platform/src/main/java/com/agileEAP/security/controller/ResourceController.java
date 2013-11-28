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
import com.agileEAP.data.TreeNode;
import com.agileEAP.infrastructure.service.ActionLogService;
import com.agileEAP.infrastructure.service.SysParamService;
import com.agileEAP.security.entity.Operate;
import com.agileEAP.security.entity.Privilege;
import com.agileEAP.security.entity.Resource;
import com.agileEAP.security.service.OperateService;
import com.agileEAP.security.service.ResourceService;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;
import com.agileEAP.utils.*;

/**
 * Resource管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /security/resource/ Create page : GET
 * /security/resource/create Create action : POST /security/resource/create
 * Update page : GET /security/resource/update/{id} Update action : POST
 * /security/resource/update Delete action : GET /security/resource/delete/{id}
 * 
 * @author trh
 */
@Controller
@RequestMapping(value = "/security/resource")
public class ResourceController {

	private static Logger logger = LoggerFactory
			.getLogger(ResourceController.class);

	@Autowired
	private ActionLogService actionLogService;

	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private OperateService operateService;
	
	@Autowired
	private SysParamService sysParamService;

	@RequestMapping(value = "")
	public String resourceManager() {
		return "security/resource/resourceManager";
	}
	
	@RequestMapping(value = "tree")
	public String tree(Model model) {
    	model.addAttribute("action", "choose");
		return "security/resource/resourceTree";
	}

	@RequestMapping(value = "treeData")
	@ResponseBody
	public List<TreeNode> ajaxDataResult(HttpServletRequest request)
			throws IOException {
/*		InputStream is = request.getInputStream();
		byte[] bytes = new byte[request.getContentLength()];
		is.read(bytes);
		String json = new String(bytes, request.getCharacterEncoding());

		String sysID=sysParamService.getSysParamValue("SysID");
		String nodeId = sysID;
		if (json != null && json.length() > 0)			
			nodeId = json.split("=")[1];

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("parentID", nodeId);
		List<Resource> resources = resourceService.search(searchParams);

		List<TreeNode> nodes = new ArrayList<TreeNode>();
		for (Resource resource : resources) {
			TreeNode node = new TreeNode();
			node.setId(resource.getId());
			node.setText(resource.getName());
			node.setExpanded(false);
			node.setIcon(request.getContextPath()+"/themes/default/images/operate/menu.png");
			searchParams.clear();
			searchParams.put("parentID", resource.getId());
			List<Resource> childrenResources = resourceService
					.search(searchParams);
			node.setHasChildren(childrenResources != null
					&& childrenResources.size() > 0);
			// node.setItems(new ArrayList<TreeNode>());
			nodes.add(node);
			// buildTreeNode(node,resource.getId());
		}*/
		
		String sysID=sysParamService.getSysParamValue("SysID");
		String sysName=sysParamService.getSysParamValue("SysName");
		TreeNode rootNode=new TreeNode();
		rootNode.setId(sysID);
		rootNode.setText(sysName);
		rootNode.setExpanded(true);
		rootNode.setHasChildren(true);
		rootNode.setIcon(request.getContextPath()+"/themes/default/images/operate/system.png");
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("parentID", rootNode.getId());
		searchParams.put("$orderby", "sortOrder");
		List<Resource> resources = resourceService.search(searchParams);		
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		for (Resource resource : resources) {
			TreeNode node = new TreeNode();
			node.setId(resource.getId());
			node.setText(resource.getName());
			node.setExpanded(false);
			node.setIcon(request.getContextPath()+"/themes/default/images/operate/menu.png");
			// node.setItems(new ArrayList<TreeNode>());
			node.setHasChildren(true);
			nodes.add(node);
			buildTreeNode(node,resource.getId(),request.getContextPath());
		}
		rootNode.setItems(nodes);
		
		List<TreeNode> roots= new ArrayList<TreeNode>();
		roots.add(rootNode);
	
		return roots;
	}

	private void buildTreeNode(TreeNode parentNode, String parentID,String contextPath) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("parentID", parentID);
		searchParams.put("$orderby", "sortOrder");
		List<Resource> resources = resourceService.search(searchParams);

		if (resources == null || resources.size() == 0)
			return;

		for (Resource resource : resources) {
			TreeNode node = new TreeNode();
			node.setId(resource.getId());
			node.setText(resource.getName());
			node.setExpanded(false);
			node.setIcon(contextPath+"/themes/default/images/operate/"+(resource.getType()==1?"menu.png":"page.png"));

			if (parentNode.getItems() == null) {
				parentNode.setItems(new ArrayList<TreeNode>());
				parentNode.setHasChildren(true);
			}
			parentNode.getItems().add(node);

			buildTreeNode(node, resource.getId(),contextPath);
		}
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> create(@RequestParam String jsonValue) {
		String message = "保存资源成功";
		Map<String,Object> retValue=new HashMap<String,Object>();
		
		try {
			JsonConvert jsonConvert = new JsonConvert();
			Resource resource = jsonConvert.fromJson(jsonValue, Resource.class);

			if (resource.getId() == null || resource.getId().length() == 0) {
				resource.setId(UUID.randomUUID().toString());
				resourceService.save(resource);
			} else {
				resourceService.update(resource);
			}
			retValue.put("id", resource.getId());
			actionLogService.AddActionLog("保存", message,
					jsonConvert.toJson(resource));
		} catch (Exception ex) {
			message = "保存资源出错，请检查输入是否正确！";
			logger.error(String.format("保存资源id={0}出错，请检查输入是否正确", jsonValue), ex);
		}

		retValue.put("message", message);
		return retValue;
	}

	@RequestMapping(value = "{id}")
	@ResponseBody
	public Resource getitem(@PathVariable("id") String id) {
		Resource resource = resourceService.get(id);
		if (resource != null) {
			Resource parentResource = resourceService.get(resource.getParentID());
			if (parentResource != null) {
				resource.setParentName(parentResource.getName());
			}
			
			resource.setOperates(operateService.findOperatesByResourceID(id));
		}

		return resource;
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam(value = "id") String id) {
		String message = "删除成功";
		try {
			resourceService.delete(id);
			actionLogService
					.AddActionLog("删除", String.format("删除角色id={0}", id));
		} catch (Exception ex) {
			message = String.format("删除角色id={0}出错，请检查输入是否正确", id);
			logger.error(message, ex);
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
			model.addAttribute("resource", resourceService.get(id));
		} else {
			model.addAttribute("resource", new Resource());
		}
	}
}