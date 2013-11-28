package com.agileEAP.workflow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.shiro.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.agileEAP.utils.*;
import com.agileEAP.data.PageDataResult;
import com.agileEAP.data.RequestPageData;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;
import com.agileEAP.infrastructure.service.ActionLogService;
import com.agileEAP.web.Servlets;
import com.agileEAP.workflow.entity.ActivityInst;
import com.agileEAP.workflow.entity.ProcessDef;
import com.agileEAP.workflow.entity.WorkItem;
import com.agileEAP.workflow.service.ActivityInstService;
import com.agileEAP.workflow.service.ProcessDefService;
import com.agileEAP.workflow.service.ProcessInstService;
import com.agileEAP.workflow.service.WorkItemService;
import com.agileEAP.workflow.viewModel.ProcessDefModel;
import com.agileEAP.workflow.viewModel.TransitionModel;
import com.agileEAP.workflow.viewModel.WorkItemModel;
import com.agileEAP.workflow.engine.*;
import com.agileEAP.workflow.definition.*;
import com.agileEAP.workflow.engine.enums.*;

/**
* 流程定义Controller
* restful风格的urls
* @author trh
*/
@Controller
@RequestMapping(value = "/workflow")
public class EngineController
{
    private static Logger logger = LoggerFactory.getLogger(EngineController.class);
	@Autowired
	private IWorkflowEngine workflowEngine;
	@Autowired
	private ActionLogService actionLogService;
	@Autowired
	private ProcessDefService processDefService;
	@Autowired
	private ProcessInstService processInstService;
	@Autowired
	private ActivityInstService activityInstService;
	@Autowired
	private WorkItemService workItemService;
	
    @RequestMapping(value = "myworkitem", method = RequestMethod.GET)
	public final String MyWorkItem()
	{
		return "workflow/engine/myWorkItem";
	}

    @RequestMapping(value = "myworkitem", method = RequestMethod.POST)
    @ResponseBody
	public final PageDataResult MyWorkItem_Filter(HttpServletRequest request,@RequestParam String entry)throws IOException {
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
		if (entry.equals("WaitExecute"))
		{
			searchParams.put("currentState", (short)WorkItemStatus.WaitExecute.getValue());
		}
		else if (entry.equals("Compeleted"))
		{
			searchParams.put("_rawsql", String.format(" CurrentState !=%1$s", (short)WorkItemStatus.WaitExecute.getValue()));
		}
    	
		String sortCommand = "order by CreateTime desc";
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		PageDataResult pageDataResult= workflowEngine.GetMyWorkItems(user.getId(), searchParams,sortCommand, requestData,false);
		if(pageDataResult.getData()!=null)
    	{
    		List<WorkItemModel> workItemModels=new ArrayList<WorkItemModel>();
    		for(Object item:pageDataResult.getData())
    		{
    			WorkItem workItem=(WorkItem)item;
    			WorkItemModel model=new WorkItemModel();
    			model.setName(workItem.getName());
    			model.setCreator(workItem.getCreator());
    			model.setCreatorName(workItem.getCreatorName());
    			model.setProcessInstID(workItem.getProcessInstID());
    			model.setProcessInstName(workItem.getProcessInstName());
    			model.setStartTime(workItem.getStartTime());
    			String currentState="待执行";
    			switch(WorkItemStatus.forValue((int)workItem.getCurrentState()))
    			{
    			case WaitExecute:
    				currentState="待执行";
    				break;
    			case Stopped:
    				currentState="停止";
    				break;
    			case Executing:
    				currentState="执行";
    				break;
    			case Suspended:
    				currentState="挂起";
    				break;
    			case Compeleted:
    				currentState="完成";
    				break;
    			case Terminated:
    				currentState="终止";
    				break;
    			case Canceled:
    				currentState="取消";
    				break;
    			case Error:
    				currentState="出错";
    				break;
    			}
    			model.setCurrentState(currentState);
    			workItemModels.add(model);
    			}
    		pageDataResult.setData(workItemModels);
    	}
		return pageDataResult;
	}

    @RequestMapping(value = "process/start", method = RequestMethod.GET)
	public final String StartProcess()
	{
		return "workflow/engine/startProcess";
	}

    @RequestMapping(value = "process/list", method = RequestMethod.POST)
    @ResponseBody
	public final PageDataResult StartProcess_Filter(HttpServletRequest request)throws IOException
	{
    	InputStream is = request.getInputStream();
    	byte[] bytes = new byte[request.getContentLength()];
    	is.read(bytes);
    	String json = new String(bytes, request.getCharacterEncoding());

    	JsonConvert jsonConvert = new JsonConvert();
    	RequestPageData requestData = jsonConvert.fromJson(json,RequestPageData.class);

    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("page", requestData.getPage());
    	searchParams.put("pageSize", requestData.getPageSize());
    	searchParams.put("currentState", (short)ProcessStatus.Release.getValue());
    	String orderby = "order by CreateTime desc";
    	searchParams.put("orderby", orderby);
    	searchParams.putAll(requestData.getData());

    	PageDataResult pageDataResult= processDefService.searchByPage(searchParams);
    	if(pageDataResult.getData()!=null)
    	{
    		List<ProcessDefModel> processDefModels=new ArrayList<ProcessDefModel>();
    		for(Object item:pageDataResult.getData())
    		{
    			ProcessDef processDef=(ProcessDef)item;
    			ProcessDefModel model=new ProcessDefModel();
    			model.setID(processDef.getId());
    			model.setCategoryID(processDef.getCategoryID());
    			model.setCurrentFlag(processDef.getCurrentFlag() == 1 ? "是" : "否");
    			model.setIsActive(processDef.getIsActive() == 1 ? "是" : "否");
    			model.setName(processDef.getName());
    			model.setText(processDef.getText());
    			model.setVersion(processDef.getVersion());
    			model.setCreateTime(processDef.getCreateTime());
    			
    			String currentState="未发布";
    			if(processDef.getCurrentState()==1)
    			{
    				 currentState="已发布";
    			}else if(processDef.getCurrentState()==2)
    			{
    				 currentState="终止";
    			}
    			
    			model.setCurrentState(currentState);
    			processDefModels.add(model);
    			}
    		pageDataResult.setData(processDefModels);
    	}

    	return pageDataResult;
	}

    @RequestMapping(value = "process", method = RequestMethod.GET)
	public final String Process()
	{
		return "workflow/engine/process";
	}

    @RequestMapping(value = "process",method = RequestMethod.POST)
    @ResponseBody
	public final PageDataResult Process_Filter(HttpServletRequest request)throws IOException
	{
    	InputStream is = request.getInputStream();
    	byte[] bytes = new byte[request.getContentLength()];
    	is.read(bytes);
    	String json = new String(bytes, request.getCharacterEncoding());

    	JsonConvert jsonConvert = new JsonConvert();
    	RequestPageData requestData = jsonConvert.fromJson(json,RequestPageData.class);

    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("page", requestData.getPage());
    	searchParams.put("pageSize", requestData.getPageSize());
    	String orderby = "order by CreateTime desc";
    	searchParams.put("orderby", orderby);
    	searchParams.putAll(requestData.getData());
    	
    	PageDataResult pageDataResult= processDefService.searchByPage(searchParams);
    	if(pageDataResult.getData()!=null)
    	{
    		List<ProcessDefModel> processDefModels=new ArrayList<ProcessDefModel>();
    		for(Object item:pageDataResult.getData())
    		{
    			ProcessDef processDef=(ProcessDef)item;
    			ProcessDefModel model=new ProcessDefModel();
    			model.setID(processDef.getId());
    			model.setCategoryID(processDef.getCategoryID());
    			model.setCurrentFlag(processDef.getCurrentFlag() == 1 ? "是" : "否");
    			model.setIsActive(processDef.getIsActive() == 1 ? "是" : "否");
    			model.setName(processDef.getName());
    			model.setText(processDef.getText());
    			model.setVersion(processDef.getVersion());
    			model.setCreateTime(processDef.getCreateTime());
    			
    			String currentState="未发布";
    			if(processDef.getCurrentState()==1)
    			{
    				 currentState="已发布";
    			}else if(processDef.getCurrentState()==2)
    			{
    				 currentState="终止";
    			}
    			
    			model.setCurrentState(currentState);
    			processDefModels.add(model);
    			}
    		pageDataResult.setData(processDefModels);
    	}

    	return pageDataResult;
	}

    @RequestMapping(value = "getProcessDefID")
    @ResponseBody
	public final String GetProcessDefID(String processInstID)
	{
		try
		{
			return processInstService.get(processInstID).getProcessDefID();
		}
		catch (RuntimeException ex)
		{
			logger.error( String.format("获取流程定义ID%1$s出错", processInstID), ex);
		}
		return null;
	}

    @RequestMapping(value = "process/delete",method = RequestMethod.POST)
    @ResponseBody
	public final Map<String,Object> DeleteProcess(String processDefID)
	{
    	Map<String,Object> values=new HashMap<String,Object>();
		String message = String.format("删除流程定义%1$s", processDefID);
		try
		{
			processDefService.delete(processDefID);
			message += "成功";
			JsonConvert jsonConvert=new JsonConvert();
			actionLogService.AddActionLog("删除", message,jsonConvert.toJson(processDefService.get(processDefID)));
		}
		catch (RuntimeException ex)
		{
			message += "出错";
			logger.error(message, ex);
			actionLogService.AddActionLog("删除",message);
		}
		values.put("message", message);
		return values;
	}

    @RequestMapping(value = "process/publish",method = RequestMethod.POST)
    @ResponseBody
	public final Map<String,Object> PublishProcess(String processDefID)
	{
    	Map<String,Object> values=new HashMap<String,Object>();
		String message = String.format("发布流程%1$s", processDefID);
		try
		{
			ProcessDef processDef = processDefService.get(processDefID);
			if (processDef != null)
			{
				processDef.setCurrentState((short)ProcessStatus.Release.getValue()); //已发布
				processDefService.update(processDef);
				message += "成功";
			}
		}
		catch (RuntimeException ex)
		{
			message += "出错";
			logger.error(message, ex);
		}
		actionLogService.AddActionLog("发布",message);

		values.put("message", message);
		values.put("currentStatus", "已发布");
		return values;
	}

    @RequestMapping(value = "process/terminate",method = RequestMethod.POST)
    @ResponseBody
	public final Map<String,Object> TerminateProcess(String processDefID)
	{
    	Map<String,Object> values=new HashMap<String,Object>();
		String message = String.format("终止流程%1$s", processDefID);
		try
		{
			ProcessDef processDef = processDefService.get(processDefID);
			if (processDef != null)
			{
				processDef.setCurrentState((short)ProcessStatus.Terminated.getValue());
				processDefService.update(processDef);
				message +=  "成功";
			}
		}
		catch (RuntimeException ex)
		{
			message += "出错";
			logger.error(message, ex);
		}
		actionLogService.AddActionLog("终止", message);

		values.put("message", message);
		values.put("currentStatus", "已终止");
		return values;
	}

	/** 
	 启动流程生成一个实例
	 
	 @return 
	*/
    @RequestMapping(value = "process/start",method = RequestMethod.POST)
    @ResponseBody
	public final Map<String,Object> StartProcessInst(@RequestParam String processDefID)
	{
		try
		{
			String processInstID = workflowEngine.CreateAProcess(processDefID);
			workflowEngine.StartAProcess(processInstID, null);
			
			Map<String,Object> parameters=new HashMap<String,Object>();
			parameters.put("ProcessInstID", processInstID);
			WorkItem wi = workItemService.search(parameters).get(0);
			
			ProcessDefine processdefine = workflowEngine.GetProcessDefine(processDefID);
			actionLogService.AddActionLog("启动流程", String.format("启动流程%1$s的实例成功", processDefID));
			
			Map<String,Object> retValue=new HashMap<String,Object>();
			retValue.put("processInstID", processInstID);
			retValue.put("workItemID", wi.getId());
			retValue.put("startURL", processdefine.getStartURL());
			
			return retValue;
		}
		catch (RuntimeException ex)
		{
			logger.error(String.format("启动流程%1$s的实例失败", processDefID), ex);
		}

		return null;
	}

	/** 
	 将当前状态改为"挂起"
	 
	 @return 
	*/
    @RequestMapping(value = "process/suspendinst",method = RequestMethod.POST)
    @ResponseBody
	public final Map<String,Object> SuspendProcessInst(@RequestParam String processInstID)
	{
    	Map<String,Object> values=new HashMap<String,Object>();
		String message = String.format("挂起流程实例%1$s", processInstID);
		try
		{
			workflowEngine.TerminateProcessInst(processInstID);
			message+="成功";
		}
		catch (RuntimeException ex)
		{
			message+="出错";
			logger.error(message, ex);
		}
		actionLogService.AddActionLog("挂起流程",message);
		values.put("currentStatus", "挂起");
		values.put("message", message);
		
		return values;
	}

    @RequestMapping(value = "process/terminateinst",method = RequestMethod.POST)
    @ResponseBody
	public final Map<String,Object>  TerminateProcessInst(@RequestParam String processInstID)
	{
    	Map<String,Object> values=new HashMap<String,Object>();
		String message = String.format("终止流程实例%1$s", processInstID);
		try
		{
			workflowEngine.TerminateProcessInst(processInstID);
			message+= "成功";
		}
		catch (RuntimeException ex)
		{
			message+=  "出错";
			logger.error(message, ex);
		}
			
		actionLogService.AddActionLog("终止流程",message);

		values.put("currentStatus", "已终止");
		values.put("message", message);
		return values;
	}

    @RequestMapping(value = "process/deleteinst",method = RequestMethod.POST)
    @ResponseBody
	public final Map<String,Object>  DeleteProcessInst(@RequestParam String processInstID)
	{
    	Map<String,Object> values=new HashMap<String,Object>();
		String message = String.format("删除流程实例%1$s", processInstID);
		try
		{
			workflowEngine.DeleteProcessInst(processInstID);
			message += "成功";
		}
		catch (RuntimeException ex)
		{
			message += "出错";
			logger.error(message, ex);
		}
		actionLogService.AddActionLog("删除流程",message);

		values.put("message", message);
		values.put("currentStatus", "已删除");
		return values;
	}

	/** 
	 将当前状态改为"运行"
	 
	 @return 
	*/
    @RequestMapping(value = "process/resumeinst",method = RequestMethod.POST)
    @ResponseBody
	public final Map<String,Object> ResumeProcessInst(HttpServletRequest request,@RequestParam String processInstID)
	{
    	Map<String,Object> values=new HashMap<String,Object>();
		String message = String.format("恢复流程实例%1$s", processInstID);
		try
		{
			workflowEngine.ResumeProcessInst(processInstID);
			message += "成功";
		}
		catch (RuntimeException ex)
		{
			message += "出错";
			logger.error(message, ex);
		}

		actionLogService.AddActionLog(Servlets.getClientIp(request),"恢复流程",message);
		
		values.put("message", message);
		values.put("currentStatus", "已启动");
		return values;
	}

    @RequestMapping(value = "workitem/detail")
	public final String WorkItemDetail(HttpServletRequest request,@RequestParam String workItemID,Model model)
	{
		try
		{
			String actionURL = "";
			WorkItem workItem = workItemService.get("workItemID");
			ActivityInst activityInst = activityInstService.get(workItem.getActivityInstID());

			String processDefID = workItem.getProcessID();
			String processInstID = workItem.getProcessInstID();
			Activity activity = workflowEngine.getWorkflowPersistence().GetActivity(workItem.getProcessID(), activityInst.getActivityDefID());
			if (activity instanceof ManualActivity)
			{
				Object tempVar = workflowEngine.getWorkflowPersistence().GetActivity(workItem.getProcessID(), activityInst.getActivityDefID());
				ManualActivity manualActivity = (ManualActivity)((tempVar instanceof ManualActivity) ? tempVar : null);
				if (manualActivity.getCustomURL().getURLType()== URLType.DefaultURL)
				{
					actionURL = "/Workflow/eForm" + request.getQueryString();
				}
				else
				{
					actionURL = String.format("%1$s%2$s", (workItem.getActionURL() != null) ? workItem.getActionURL() : manualActivity.getCustomURL().getSpecifyURL(), request.getQueryString());
				}
			}
			else
			{
				actionURL = String.format("%1$s%2$s", workItem.getActionURL(), request.getQueryString());
			}

			if (!actionURL.startsWith("/"))
			{
				actionURL = "/" + actionURL;
			}
			List<TransitionModel>  processTransitions=
					workflowEngine.getWorkflowPersistence().GetProcessInstTransitions(processInstID);
			
			model.addAttribute("url", actionURL);
			model.addAttribute("processDefID", processDefID);
			model.addAttribute("processInstID", processInstID);
			model.addAttribute("workflowTransitions", processTransitions);
		}
		catch (RuntimeException ex)
		{
			logger.error("查看工作项"+workItemID+"出错",ex);
		}

		return "workflow/engine/workitemDetail";
	}   
}