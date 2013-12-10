package com.agileEAP.workflow.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agileEAP.infrastructure.service.ActionLogService;
import com.agileEAP.infrastructure.service.EFormService;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;
import com.agileEAP.utils.Identities;
import com.agileEAP.workflow.definition.ProcessDefine;
import com.agileEAP.workflow.engine.IWorkflowEngine;
import com.agileEAP.workflow.entity.ActivityInst;
import com.agileEAP.workflow.entity.ProcessDef;
import com.agileEAP.workflow.entity.TransControl;
import com.agileEAP.workflow.service.ActivityInstService;
import com.agileEAP.workflow.service.ProcessDefService;
import com.agileEAP.workflow.service.TransControlService;

@Controller
@RequestMapping(value = "/workflow/designer")
public class WorkflowDesignerController {
	private static Logger logger = LoggerFactory
			.getLogger(WorkflowDesignerController.class);
	@Autowired
	private IWorkflowEngine workflowEngine;
	@Autowired
	private ActionLogService actionLogService;
	@Autowired
	private ProcessDefService processDefService;
	@Autowired
	private ActivityInstService activityInstService;
	@Autowired
	private TransControlService transControlService;
	@Autowired
	private EFormService eFormService;

	@RequestMapping(value = "")
	public final String designer() {
		return "workflow/designer/workflowDesigner";
	}

	@RequestMapping(value = "processdefine/save", method = RequestMethod.POST)
	@ResponseBody
	public final String SaveProcessDefine(
			@RequestParam String processDefContent, @RequestParam String name,
			@RequestParam String text, @RequestParam String version,
			@RequestParam String description, @RequestParam String startor,
			@RequestParam String isActive, @RequestParam String categoryID,
			@RequestParam String currentFlag,
			@RequestParam String currentStatus,
			@RequestParam String processDefID) {
		String message = "操作成功";
		try {
			/*
			 * ProcessDefine processDefine
			 * =ProcessDefine.parseFromJson(processDefContent) ;
			 * processDefContent = processDefine.toXml();
			 */
			if (processDefContent != null && processDefContent.length() > 0) {
				String id = processDefID != null && processDefID.length() > 0 ? processDefID
						: Identities.uuid();
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("name", name);// processDefine.getID());
				parameters.put("version", version);// processDefine.getVersion());
				parameters.put("_rawsql", "id!='" + processDefID + "'");
				List<ProcessDef> proDefs = processDefService.search(parameters);
				if (proDefs != null && proDefs.size() > 0) {
					message = "操作失败，该流程已经存在，选择该流程修改";
				} else {
					ProcessDef processDef = new ProcessDef();
					processDef.setId(id);
					processDef.setName(name);
					processDef.setText(text);
					processDef.setVersion(version);
					processDef.setDescription(description);
					processDef.setCreateTime(new java.util.Date());
					ShiroUser user = (ShiroUser) SecurityUtils.getSubject()
							.getPrincipal();
					processDef.setCreator(user.getLoginName());
					processDef.setStartor(startor);
					processDef.setIsActive(Short.parseShort(isActive));
					processDef.setCurrentFlag(Short.parseShort(currentFlag));
					processDef.setCurrentState(Short.parseShort(currentStatus));
					processDef.setContent(processDefContent);
					processDef.setCategoryID(categoryID);

					processDefService.saveOrUpdate(processDef);
				}
			}
		} catch (RuntimeException ex) {
			logger.error("创建工作流失败,processDefContent=" + processDefContent, ex);
			message = "操作失败";
		}
		return message;
	}

	@RequestMapping(value = "getProcessDef", method = RequestMethod.POST)
	@ResponseBody
	public final ProcessDef GetProcessDefine(@RequestParam String processDefID) {
		return processDefService.get(processDefID);
	}

	@RequestMapping(value = "getProcessInfo")
	@ResponseBody
	public final Map<String, Object> GetProcessInfo(
			@RequestParam String processDefID,
			@RequestParam(required = false) String processInstID) {
		Map<String, Object> retValue = new HashMap<String, Object>();
		if (processDefID != null && processDefID.length() > 0) {
			ProcessDef processDef = processDefService.get(processDefID);
			// ProcessDefine processDefine
			// =WFUtil.parseProcessDefine(processDef.getContent());
			ProcessDefine processDefine = ProcessDefine
					.parseFromJson(processDef.getContent());
			if (processInstID != null && processInstID.length() > 0) {
				List<TransControl> transList;
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("processInstID", processInstID);
				parameters.put("orderby", "order by createTime asc");
				List<ActivityInst> activityInsts = activityInstService
						.search(parameters);
				if (activityInsts != null && activityInsts.size() > 0) {
					parameters.clear();
					parameters.put("processInstID", processInstID);
					parameters.put("orderby", "order by transTime desc");
					transList = transControlService.search(parameters);
				} else {
					transList = new ArrayList<TransControl>();
				}

				retValue.put("processDefID", processDefID);
				retValue.put("processInstID", processInstID);
				retValue.put("processDefine", processDefine);
				retValue.put("activityInsts", activityInsts);
				retValue.put("transList", transList);
			} else {
				retValue.put("processDefID", processDefID);
				retValue.put("processInstID", processInstID);
				retValue.put("processDefine", processDefine);
			}
		}

		return retValue;
	}

	@RequestMapping(value = "connection")
	public final String ConnectionDetail() {
		return "workflow/designer/connectionDetail";
	}

	@RequestMapping(value = "activity")
	public final String ActivityDetail() {
		return "workflow/designer/activityDetail";
	}

	@RequestMapping(value = "getProcessDefContent")
	@ResponseBody
	public final String GetprocessDefContent(
			@RequestParam String processDefContent) {
		try {
			return ProcessDefine.parseFromJson(processDefContent).toXml();
		} catch (RuntimeException ex) {
			logger.error("获取转换工作流定义失败processDefContent=" + processDefContent,
					ex);
		}
		return "";
	}

	@RequestMapping(value = "getProcessDefine")
	@ResponseBody
	public final ProcessDefine getProcessDefine(
			@RequestParam String processDefContent) {
		return ProcessDefine.parseFromXml(processDefContent);
	}

	@RequestMapping(value = "participantor")
	public final String participantor() {
		return "workflow/designer/participantor";
	}
}