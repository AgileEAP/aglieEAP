package com.agileEAP.workflow.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agileEAP.infrastructure.entity.EForm;
import com.agileEAP.infrastructure.service.EFormService;
import com.agileEAP.workflow.definition.*;
import com.agileEAP.workflow.entity.*;
import com.agileEAP.workflow.service.*;
import com.agileEAP.workflow.engine.*;
import com.agileEAP.workflow.engine.utility.WFUtil;

@Controller
@RequestMapping(value = "/workflow/designer/old")
public class DesignerController
{
	@Autowired
	private IWorkflowEngine workflowEngine;
	@Autowired
	private ProcessDefService processDefService;
	@Autowired
	private ActivityInstService activityInstService;
	@Autowired
	private TransControlService transControlService;
	@Autowired
	private EFormService eFormService;

	@RequestMapping(value = "")
	public final String designer()
	{
		return "workflow/designer";
	}
	
	@RequestMapping(value = "activity")
	public final String ActivityDetail(@RequestParam String processDefID,@RequestParam String activityID,@RequestParam int activityType,Model model)
	{
		
		if (activityType == (short)ActivityType.ManualActivity.getValue())
		{
			ProcessDefine processDefine =workflowEngine.GetProcessDefine(processDefID);
			if (processDefine != null)
			{
				Activity activity = processDefine.getActivity(activityID);
				ManualActivity manualActivity = (ManualActivity)((activity instanceof ManualActivity) ? activity : null);
				//参与者
				model.addAttribute("participant", manualActivity.getParticipant().getParticipantors());
				EForm form=	eFormService.get(manualActivity.geteForm());
				if(form!=null)
				{
					model.addAttribute("form", eFormService.get(manualActivity.geteForm()).getContent());
				}
				//触发事件
				model.addAttribute("triggerEvents", manualActivity.getTriggerEvents());
				//回退
				model.addAttribute("rollBack", manualActivity.getRollBack().getParameters());
				//自由流
				model.addAttribute("freeFlowRule", manualActivity.getFreeFlowRule().getFreeRangeActivities());
				//chbIsFreeActivity.Checked = manualActivity.FreeFlowRule.IsFreeActivity; //设置该活动为自由活动
				//if (manualActivity.FreeFlowRule.FreeRangeStrategy == FreeRangeStrategy.FreeWithinProcess)
				//{
				//    rblFreeWithinProcess.Checked = true;
				//}
				//else if (manualActivity.FreeFlowRule.FreeRangeStrategy == FreeRangeStrategy.FreeWithinActivities)
				//{
				//    rblFreeWithinActivities.Checked = true;
				//}
				//else if (manualActivity.FreeFlowRule.FreeRangeStrategy == FreeRangeStrategy.FreeWithinNextActivites)
				//{
				//    rblFreeWithinNextActivites.Checked = true;
				//}; 
				//自由范围设置策略
				//ViewData["FreeFlowRule"] = manualActivity.FreeFlowRule;
				//rptFreeRange.DataSource = manualActivity.FreeFlowRule.FreeRangeActivities;
				//rptFreeRange.DataBind();

				//chbIsOnlyLimitedManualActivity.Checked = manualActivity.FreeFlowRule.IsOnlyLimitedManualActivity; //流向的目标活动仅限于人工活动

				//启动策略
				// ViewData["ActivateRule"] = manualActivity.ActivateRule;
				//if (manualActivity.ActivateRule.ActivateRuleType == ActivateRuleType.DirectRunning)
				//{
				//    rblDirectRunning.Checked = true;
				//}
				//else if (manualActivity.ActivateRule.ActivateRuleType == ActivateRuleType.WaitActivate)
				//{
				//    rblDisenabled.Checked = true;
				//}
				//else if (manualActivity.ActivateRule.ActivateRuleType == ActivateRuleType.AutoSkip)//可选规则
				//{
				//    rblAutoAfter.Checked = true;
				//}
				//txtActivateRuleApp.Text = manualActivity.ActivateRule.ActivateRuleApp; //规则逻辑
				//重新设置Participant
				// ViewData["ResetParticipant"] = manualActivity.ResetParticipant;
				//if (manualActivity.ResetParticipant == ResetParticipant.FirstParticipantor)
				//{
				//    rbFirstParticipantor.Checked = true;
				//}
				//else if (manualActivity.ResetParticipant == ResetParticipant.LastParticipantor)  //重新启动规则
				//{
				//    rbLastParticipantor.Checked = true;
				//}
				//重新设置URL
				// ViewData["ResetURL"] = manualActivity.ResetURL;
				//chbIsSpecifyURL.Checked = manualActivity.ResetURL.IsSpecifyURL; 
				//cboURLType.BindCombox(URLType.DefaultURL);
				//cboURLType.SelectedValue = ((int)(object)manualActivity.ResetURL.URLType).ToSafeString(); //URL类型
				//txt2SpecifyURL.Text = manualActivity.ResetURL.SpecifyURL;
			}
			else
			{
				model.addAttribute("participant", new ArrayList<Participantor>());
				model.addAttribute("triggerEvents", new ArrayList<TriggerEvent>());
				model.addAttribute("freeFlowRule", new ArrayList<Activity>());
			}
		}
		return "workflow/activity";
	}
	
	@RequestMapping(value = "getProcessInfo")
	@ResponseBody
	public final Map<String, Object> GetProcessInfo(@RequestParam String processDefID,@RequestParam String processInstID)
	{
		Map<String, Object> retValue=new HashMap<String,Object>();
		if (processDefID!=null&&processDefID.length()>0)
		{
			ProcessDef processDef =processDefService.get(processDefID);
			ProcessDefine processDefine =WFUtil.parseProcessDefine(processDef.getContent());
			if (processInstID!=null&&processInstID.length()>0)
			{
				List<TransControl> transList;
					Map<String, Object> parameters=new HashMap<String,Object>();
					parameters.put("processInstID", processInstID);
					parameters.put("orderby", "order by createTime asc");
					List<ActivityInst> activityInsts =activityInstService.search(parameters);
					if (activityInsts != null && activityInsts.size() > 0)
					{
						parameters.clear();
						parameters.put("processInstID", processInstID);
						parameters.put("orderby", "order by transTime desc");
						transList = transControlService.search(parameters);
					}
					else
					{
						transList= new ArrayList<TransControl>();
					}

					retValue.put("processDefID", processDefID);
					retValue.put("processInstID", processInstID);
					retValue.put("processDefine", processDefine);
					retValue.put("activityInsts", activityInsts);
					retValue.put("transList", transList);
			}
			else
			{
					retValue.put("processDefID", processDefID);
					retValue.put("processInstID", processInstID);
					retValue.put("processDefine", processDefine);
			}
		}

		return retValue;
	}
	
	@RequestMapping(value = "connection")
	public final String ConnectionDetail()
	{
		return "workflow/connectionDetail";
	}
}