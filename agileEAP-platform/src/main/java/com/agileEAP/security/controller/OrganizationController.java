package com.agileEAP.security.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.agileEAP.data.PageDataResult;
import com.agileEAP.data.RequestPageData;
import com.agileEAP.data.TreeNode;
import com.agileEAP.infrastructure.service.ActionLogService;
import com.agileEAP.infrastructure.service.SysParamService;
import com.agileEAP.security.entity.Organization;
import com.agileEAP.security.service.OrganizationService;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;
import com.agileEAP.utils.JsonConvert;

/**
 * 组织机构 Controller restful风格的urls list page : GET /security/organization/ add
 * page : GET /security/organization/add add action : POST
 * /security/organization/add update page : GET
 * /security/organization/update/{id} update action : POST
 * /security/organization/update delete action : POST
 * /security/organization/delete
 * 
 * @author trh
 */
@Controller
@RequestMapping(value = "/security/organization")
public class OrganizationController {
	private static Logger logger = LoggerFactory
			.getLogger(OrganizationController.class);

	@Autowired
	private ActionLogService actionLogService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private SysParamService sysParamService;

	@RequestMapping(value = "tree")
	public String tree() {
		return "security/organization/organizationTree";
	}

	@RequestMapping(value = "manager")
	public String manager() {
		return "security/organization/organizationManager";
	}

	@RequestMapping(value = "data")
	@ResponseBody
	public List<TreeNode> treeData(HttpServletRequest request)
			throws IOException {
		InputStream is = request.getInputStream();
		byte[] bytes = new byte[request.getContentLength()];
		is.read(bytes);
		String json = new String(bytes, request.getCharacterEncoding());
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		if (json == null || json.length() == 0) {
			String rootOrgID = sysParamService.getSysParamValue("RootOrgID");
			Organization rootOrganization = organizationService.get(rootOrgID);
			TreeNode rootNode = new TreeNode();
			rootNode.setId(rootOrganization.getId());
			rootNode.setText(rootOrganization.getName());
			rootNode.setExpanded(true);
			rootNode.setHasChildren(true);
			rootNode.setIcon(request.getContextPath()
					+ "/themes/default/images/operate/system.png");
			nodes.add(rootNode);
		} else {
			String nodeId = json.split("=")[1];
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("parentID", nodeId);
			searchParams.put("$orderby", "sortOrder");
			List<Organization> resources = organizationService
					.search(searchParams);

			for (Organization resource : resources) {
				TreeNode node = new TreeNode();
				node.setId(resource.getId());
				node.setText(resource.getName());
				node.setExpanded(false);
				node.setIcon(request.getContextPath()
						+ "/themes/default/images/operate/menu.png");
				searchParams.clear();
				searchParams.put("parentID", resource.getId());
				List<Organization> childrenResources = organizationService
						.search(searchParams);
				node.setHasChildren(childrenResources != null
						&& childrenResources.size() > 0);
				nodes.add(node);
			}
		}

		return nodes;
	}

	@RequestMapping(value = "")
	public String list() {
		return "security/organization/organizationList";
	}

	@RequestMapping(value = "ajaxList")
	@ResponseBody
	public PageDataResult ajaxDataResult(HttpServletRequest request)
			throws IOException {
		InputStream is = request.getInputStream();
		byte[] bytes = new byte[request.getContentLength()];
		is.read(bytes);
		String json = new String(bytes, request.getCharacterEncoding());

		JsonConvert jsonConvert = new JsonConvert();
		RequestPageData requestData = jsonConvert.fromJson(json,
				RequestPageData.class);

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("page", requestData.getPage());
		searchParams.put("pageSize", requestData.getPageSize());
		searchParams.putAll(requestData.getData());
		PageDataResult pageDataResult = organizationService
				.searchByPage(searchParams);

		return pageDataResult;
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String createForm(@RequestParam(required = false) String parentId,
			@RequestParam(required = false) String parentName, Model model) {
		Organization organization = new Organization();

		if (parentId != null && parentId.length() > 0) {
			organization.setParentID(parentId);
			organization.setParentName(parentName);
			String newCode = organizationService.newOrgId(organization
					.getParentID());
			organization.setCode(newCode);
			organization.setSortOrder(Integer.parseInt(newCode
					.substring(parentId.length())));
		}
		model.addAttribute("organization", organization);
		model.addAttribute("action", "add");
		return "security/organization/organizationDetail";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> create(
			@Valid @ModelAttribute("organization") Organization organization) {
		String message = "新增组织机构 成功";
		Map<String, String> retValue = new HashMap<String, String>();
		try {
			organization.setId(organizationService.newOrgId(organization
					.getParentID()));
			ShiroUser user = (ShiroUser) SecurityUtils.getSubject()
					.getPrincipal();
			if (user != null) {
				organization.setCreator(user.id);
			}
			organization.setCreateTime(new Date());
			organizationService.save(organization);
			JsonConvert jsonConvert = new JsonConvert();
			actionLogService.AddActionLog("新增", message,
					jsonConvert.toJson(organization));
			retValue.put("id", organization.getId());
		} catch (Exception ex) {
			message = "新增组织机构 出错，请检查输入是否正确！";
			logger.error(
					String.format("新增组织机构 id=%s出错，请检查输入是否正确",
							organization.getId()), ex);
		}
		retValue.put("message", message);
		return retValue;
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model model) {
		Organization organization = organizationService.get(id);
		Organization parentOrganization = organizationService.get(organization
				.getParentID());
		if (parentOrganization != null)
			organization.setParentName(parentOrganization.getName());
		model.addAttribute("organization", organization);
		model.addAttribute("action", "update");
		return "security/organization/organizationDetail";
	}

	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") String id, Model model) {
		model.addAttribute("organization", organizationService.get(id));
		model.addAttribute("action", "view");
		return "security/organization/organizationDetail";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> update(
			@Valid @ModelAttribute("organization") Organization organization,
			RedirectAttributes redirectAttributes) {
		Map<String, String> retValue = new HashMap<String, String>();
		String message = "更新成功";
		try {
			organizationService.update(organization);
			retValue.put("id", organization.getId());
			JsonConvert jsonConvert = new JsonConvert();
			actionLogService.AddActionLog("更新",
					String.format("更新组织机构 id=%s", organization.getId()),
					jsonConvert.toJson(organization));
		} catch (Exception ex) {
			message = "更新出错，请检查输入是否正确！";
			logger.error(
					String.format("更新组织机构 id=%s出错，请检查输入是否正确",
							organization.getId()), ex);
		}
		retValue.put("message", message);
		return retValue;
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam(value = "id") String id) {
		String message = "删除成功";
		try {
			List<String> ids = new ArrayList<String>();
			String[] values = id.split(",");
			if (values != null) {
				for (String fid : values) {
					if (fid != null && fid.length() > 0) {
						ids.add(fid);
					}
				}
			}
			organizationService.batchDelete(ids);
			actionLogService.AddActionLog("删除",
					String.format("删除组织机构 id=%s", id));
		} catch (Exception ex) {
			message = String.format("删除组织机构 id=%s出错，请检查输入是否正确", id);
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
			model.addAttribute("organization", organizationService.get(id));
		} else {
			model.addAttribute("organization", new Organization());
		}
	}
}