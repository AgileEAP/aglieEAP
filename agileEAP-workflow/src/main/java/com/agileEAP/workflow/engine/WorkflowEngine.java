package com.agileEAP.workflow.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agileEAP.data.PageDataResult;
import com.agileEAP.data.RequestPageData;
import com.agileEAP.security.entity.Operator;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;
import com.agileEAP.utils.Identities;
import com.agileEAP.workflow.definition.Activity;
import com.agileEAP.workflow.definition.ActivityType;
import com.agileEAP.workflow.definition.ManualActivity;
import com.agileEAP.workflow.definition.Participantor;
import com.agileEAP.workflow.definition.ParticipantorType;
import com.agileEAP.workflow.definition.ProcessDefine;
import com.agileEAP.workflow.engine.enums.ActivityInstStatus;
import com.agileEAP.workflow.engine.enums.DelegateType;
import com.agileEAP.workflow.engine.enums.PartiInType;
import com.agileEAP.workflow.engine.enums.ProcessInstStatus;
import com.agileEAP.workflow.engine.enums.ProcessStatus;
import com.agileEAP.workflow.engine.enums.WorkItemBizStatus;
import com.agileEAP.workflow.engine.enums.WorkItemStatus;
import com.agileEAP.workflow.engine.exception.WorkflowException;
import com.agileEAP.workflow.engine.utility.WFUtil;
import com.agileEAP.workflow.entity.ActivityInst;
import com.agileEAP.workflow.entity.Participant;
import com.agileEAP.workflow.entity.ProcessDef;
import com.agileEAP.workflow.entity.ProcessInst;
import com.agileEAP.workflow.entity.TransControl;
import com.agileEAP.workflow.entity.Transition;
import com.agileEAP.workflow.entity.WorkItem;

/** 
 工作流引擎
 
*/
@Component("workflowEngine")
public class WorkflowEngine implements IWorkflowEngine
{
	private Logger logger = LoggerFactory.getLogger(WorkflowEngine.class);
	
	@Autowired
	private IWorkflowPersistence workflowPersistence;	
	@Autowired
	private IParticipantorService participantorService;

	/** 
	 流程数据接口
	 
	*/
	public final IWorkflowPersistence getWorkflowPersistence()
	{
		return workflowPersistence;
	}

	/** 
	 创建工作流
	 
	 @param processDefID 流程定义ID
	 @return 
	*/
	public final String CreateAProcess(String processDefID)
	{
		return CreateAProcess(processDefID, "", "");
	}

	/** 
	 创建流程实例
	 
	 @param processDefID 流程定义ID
	 @param parentProcessDefID 父流程ID
	 @param parentActivityID 父活动ID
	 @return 
	*/
	public final String CreateAProcess(String processDefID, String parentProcessDefID, String parentActivityID)
	{
		ProcessDef processDef =workflowPersistence.GetProcessDef(processDefID) ;
		if (processDef == null)
		{
			WorkflowException workflowException = new WorkflowException(String.format("ID=%1$s的流程不存在", processDefID));
			WFUtil.HandleException(workflowException);

			return "";
		}

		if (processDef.getCurrentState() == (short)ProcessStatus.Candidate.getValue())
		{
			WorkflowException workflowException = new WorkflowException(String.format("ID=%1$s的流程还未发布，不能创建流程", processDefID));
			WFUtil.HandleException(workflowException);

			return "";
		}

		java.util.Date startTime = new java.util.Date( new java.util.Date().getTime()-2000);
		ProcessDefine define = GetProcessDefine(processDefID);

		ProcessInst processInst = new ProcessInst();
		processInst.setId(Identities.uuid());
		processInst.setProcessDefID(processDefID);
		processInst.setCreateTime(new java.util.Date());
		processInst.setCreator(WFUtil.getUser().getId());
		processInst.setCurrentState((short)ProcessInstStatus.NoStart.getValue());
		processInst.setIsTimeOut((short)0);
		processInst.setName(processDef.getText());
		processInst.setDescription(processDef.getDescription());
		processInst.setParentProcessID(parentProcessDefID);
		processInst.setParentActivityID(parentActivityID);
		processInst.setStartTime(startTime);
		processInst.setEndTime(WFUtil.MaxDate);
		processInst.setFinalTime(WFUtil.MaxDate);
		processInst.setLimitTime(WFUtil.MaxDate);
		processInst.setProcessDefName(processDef.getText());
		processInst.setProcessVersion(processDef.getVersion());
		processInst.setRemindTime(WFUtil.MaxDate);
		processInst.setTimeOutTime(WFUtil.MaxDate);

		Activity ad = workflowPersistence.GetStartActivity(processDefID);
		ActivityInst activityInst = new ActivityInst();
		activityInst.setId(Identities.uuid());
		activityInst.setActivityDefID(ad.getID());
		activityInst.setCreateTime(new java.util.Date());
		activityInst.setCurrentState((short)ActivityInstStatus.Compeleted.getValue());
		activityInst.setDescription(ad.getDescription());
		activityInst.setEndTime(WFUtil.MaxDate);
		activityInst.setName(String.format("启动%1$s", processDef.getText()));
		activityInst.setProcessInstID(processInst.getId());
		activityInst.setRollbackFlag((short)0);
		activityInst.setStartTime(startTime);
		activityInst.setSubProcessInstID("");
		activityInst.setType((short)ActivityType.StartActivity.getValue());
		
		workflowPersistence.SaveProcessInst(processInst);
		workflowPersistence.SaveActivityInst(activityInst);
		if (processDef.getIsActive() != 1)
		{
			processDef.setIsActive((short)1);
			workflowPersistence.SaveProcessDef(processDef);
		}

		return processInst.getId();
	}

	/** 
	 开始启动一个流程
	 
	 @param processInstID 流程实例ID
	@param parameters 启动参数
	*/
	public final void StartAProcess(String processInstID, Map<String, Object> parameters)
	{
		ProcessInst processInst = GetToRunProcessInst(processInstID, WFUtil.getUser());
		ProcessDefine processDefine = workflowPersistence.GetProcessDefine(processInst.getProcessDefID());
		ProcessContext processContext = new ProcessContext(processDefine,processInst,parameters);

		ProcessRuntime runtime = new ProcessRuntime(workflowPersistence,processContext,null);
		runtime.Run();
	}

	/** 
	 开始启动一个流程
	 
	 @param processInstID 启动
	*/
	public final void StartAProcess(String processInstID)
	{
		StartAProcess(processInstID, null);
	}

	/** 
	 获取一个流程实例
	 
	 @param processInstID 流程实例ID
	 @return 
	*/
	private ProcessInst GetToRunProcessInst(String processInstID, ShiroUser user)
	{
		ProcessInst processInst = workflowPersistence.GetProcessInst(processInstID);
		ProcessDefine processDefine = GetProcessDefine(processInst.getProcessDefID());
		String processDefID = processInst.getProcessDefID();

		//如果流程还未启动，产生一个活动实例
		if (processInst.getCurrentState() == (short)ProcessInstStatus.NoStart.getValue())
		{
			Activity srcActivity = processDefine.getStartActivity();
			ActivityInst srcActInst = workflowPersistence.GetActivityInsts(processInstID, srcActivity.getID(), ActivityInstStatus.Compeleted).get(0);
			List<Activity> destActivities = workflowPersistence.GetOutActivities(processDefID, srcActivity.getID());

				for (Activity destActivity : destActivities)
				{
					java.util.Date startTime = new java.util.Date(new java.util.Date().getTime()-1000);
					ActivityInst destActInst = new ActivityInst();
					destActInst.setActivityDefID(destActivity.getID());
					destActInst.setCurrentState((short)ActivityInstStatus.NoStart.getValue());
					destActInst.setId(Identities.uuid());
					destActInst.setCreateTime(startTime);
					destActInst.setDescription(destActivity.getDescription());
					destActInst.setEndTime(WFUtil.MaxDate);
					destActInst.setName(destActivity.getName());
					destActInst.setProcessInstID(processInstID);
					destActInst.setRollbackFlag((short)0);
					destActInst.setStartTime(startTime);
					destActInst.setSubProcessInstID("");
					destActInst.setType((short)destActivity.getActivityType().getValue());
					
					Transition transition = new Transition();
					transition.setId(Identities.uuid());
					transition.setDestActID(destActivity.getID());
					transition.setDestActInstID(destActInst.getId());
					transition.setDestActInstName(destActInst.getName());
					transition.setDestActName(destActivity.getName());
					transition.setProcessInstID(processInstID);
					transition.setProcessInstName(processInst.getName());
					transition.setSrcActID(srcActivity.getID());
					transition.setSrcActInstID(srcActInst.getId());
					transition.setSrcActInstName(srcActInst.getName());
					transition.setSrcActName(srcActivity.getName());
					transition.setTransTime(startTime);
					
					TransControl transControl = new TransControl();
					transControl.setId(Identities.uuid());
					transControl.setDestActID(destActivity.getID());
					transControl.setDestActName(destActivity.getName());
					transControl.setProcessInstID(processInst.getId());
					transControl.setSrcActID(srcActivity.getID());
					transControl.setSrcActName(srcActivity.getName());
					transControl.setTransTime(startTime);
					transControl.setTransWeight(100);
					
					if (destActivity instanceof ManualActivity)
					{
						ManualActivity ma = (ManualActivity)((destActivity instanceof ManualActivity) ? destActivity : null);
						WorkItem workItem = new WorkItem();
						workItem.setId(Identities.uuid());
						workItem.setActionMask("");
						workItem.setActionURL(ma.getCustomURL().getSpecifyURL());
						workItem.setActivityInstID(destActInst.getId());
						workItem.setActivityInstName(destActInst.getName());
						workItem.setAllowAgent((short)(ma.getAllowAgent() ? 1 : 0));
						workItem.setBizState((short)WorkItemBizStatus.Common.getValue());
						workItem.setCreateTime(startTime);
						workItem.setCreator(user.getId());
						workItem.setCreatorName(user.getName());
						workItem.setCurrentState((short)WorkItemStatus.WaitExecute.getValue());
						workItem.setDescription(destActInst.getDescription());
						workItem.setEndTime(WFUtil.MaxDate);
						workItem.setExecutor("");
						workItem.setExecutorName("");
						workItem.setIsTimeOut((short)0);
						workItem.setName(destActInst.getName());
						workItem.setParticipant("");
						workItem.setProcessID(processDefID);
						workItem.setProcessInstID(processInst.getId());
						workItem.setProcessInstName(processInst.getName());
						workItem.setProcessName(processDefine.getName());
						workItem.setRemindTime(WFUtil.MaxDate);
						workItem.setRootProcessInstID("");
						workItem.setStartTime(startTime);
						workItem.setTimeOutTime(WFUtil.MaxDate);
						workItem.setType(destActInst.getType());
						
						Participant participant = new Participant();
						participant.setId(Identities.uuid());
						participant.setCreateTime(startTime);
						participant.setDelegateType((short)DelegateType.Sponsor.getValue());
						participant.setName(user.getName());
						participant.setParticipantID(user.getId());
						participant.setParticipantIndex(1);
						participant.setParticipantType((short)ParticipantorType.Person.getValue());
						participant.setPartiInType((short)PartiInType.Exe.getValue());
						participant.setWorkItemID(workItem.getId());
						participant.setWorkItemState(workItem.getCurrentState());
						
						workflowPersistence.SaveParticipant(participant);
						workflowPersistence.SaveWorkItem(workItem);
					}
					srcActInst.setCurrentState((short)ActivityInstStatus.Compeleted.getValue());
					workflowPersistence.UpdateActivityInst(srcActInst);
					workflowPersistence.SaveActivityInst(destActInst);
					workflowPersistence.SaveTransition(transition);
					workflowPersistence.SaveTransControl(transControl);
				}
		}

		return processInst;
	}

	/** 
	 获取流程定义
	 
	 @param processDefID 流程定义ID
	 @return 
	*/
	public final ProcessDefine GetProcessDefine(String processDefID)
	{
		return workflowPersistence.GetProcessDefine(processDefID);
	}
	/** 
	 挂起流程实例
	 
	 @param processInstID 流程实例ID
	*/
	public final void SuspendProcessInst(String processInstID)
	{
/*		ProcessInst processInst = workflowPersistence.GetProcessInst(processInstID);
		repository.<ActivityInst>ExecuteNonQuery(String.format("Update WF_ActivityInst Set CurrentState=%1$s Where ProcessInstID='%2$s' and CurrentState=%3$s and RollbackFlag=0", (short)ActivityInstStatus.Suspended.getValue(), processInstID, (short)ActivityInstStatus.Running.getValue()));
		repository.<WorkItem>ExecuteNonQuery(String.format("Update WF_WorkItem Set CurrentState=%1$s Where ProcessInstID='%2$s' and CurrentState=%3$s", (short)WorkItemStatus.Suspended.getValue(), processInstID, (short)WorkItemStatus.WaitExecute.getValue()));
		processInst.CurrentState = (short)ProcessInstStatus.Suspended.getValue();
		repository.SaveOrUpdate(processInst);*/
		
		workflowPersistence.UpdateActivityInstStatus(processInstID, ActivityInstStatus.Running,ActivityInstStatus.Suspended);
		workflowPersistence.UpdateWorkItemStatus(processInstID, WorkItemStatus.WaitExecute,WorkItemStatus.Suspended);
		workflowPersistence.UpdateProcessInstStatus(processInstID,ProcessInstStatus.Suspended);
	}

	/** 
	 恢复流程实例
	 
	 @param processInstID 流程实例ID
	*/
	public final void ResumeProcessInst(String processInstID)
	{
		workflowPersistence.UpdateActivityInstStatus(processInstID, ActivityInstStatus.Suspended,ActivityInstStatus.Running);
		workflowPersistence.UpdateWorkItemStatus(processInstID, WorkItemStatus.Suspended,WorkItemStatus.WaitExecute);
		workflowPersistence.UpdateProcessInstStatus(processInstID,ProcessInstStatus.Running);
	}

	/** 
	 终止流程实例
	 
	 @param processInstID 流程实例ID
	*/
	public final void TerminateProcessInst(String processInstID)
	{
		workflowPersistence.UpdateActivityInstStatus(processInstID, ActivityInstStatus.Running,ActivityInstStatus.Terminated);
		workflowPersistence.UpdateWorkItemStatus(processInstID, WorkItemStatus.WaitExecute,WorkItemStatus.Terminated);
		workflowPersistence.UpdateProcessInstStatus(processInstID,ProcessInstStatus.Terminated);
	}

	/** 
	 删除流程实例
	 
	 @param processInstID 流程实例ID
	*/
	public final void DeleteProcessInst(String processInstID)
	{
		workflowPersistence.DeleteProcessInst(processInstID);
	}
	/** 
	 完成活动实例
	 
	 @param activityInstID 活动实例ID
	*/
	public final void CompleteActivityInst(String activityInstID)
	{
		ActivateActivityInst(activityInstID);
		//Map<String,Object> parameters=new HashMap<String,Object>();
		//parameters.put("activityInstID", activityInstID);
		//(wi.CurrentState ==WorkItemStatus.WaitExecute || wi.CurrentState ==WorkItemStatus.Suspended)
		//parameters.put("_rawsql", "currentState in (1,4)");
		
		for (WorkItem wi : workflowPersistence.GetWorkItems(activityInstID))
		{
			if(wi.getCurrentState() ==(short)WorkItemStatus.WaitExecute.getValue()|| wi.getCurrentState() ==(short)WorkItemStatus.Suspended.getValue())
			{
				CompleteWorkItem(wi.getId(), null,null);
			}
		}
	}

	/** 
	 重启活动实例
	 
	 @param activityInstID 活动实例ID
	*/
	public final void ResetActivityInst(String activityInstID)
	{
		ActivateActivityInst(activityInstID);
		//var actInst = repository.GetDomain<ActivityInst>(activityInstID);
		//UnitOfWork.ExecuteWithTrans<ActivityInst>(() =>
		//{
		//    foreach (var wi in repository.Query<WorkItem>().Where(wi => wi.ActivityInstID == activityInstID).ToArray())
		//    {
		//        wi.CurrentState = (short)WorkItemStatus.WaitExecute;
		//        repository.SaveOrUpdate(wi);
		//    }
		//});
	}

	/** 
	 挂起活动实例
	 
	 @param activityInstID 活动实例ID
	*/
	public final void SuspendActivityInst(String activityInstID)
	{
		for (WorkItem wi : workflowPersistence.GetWorkItems(activityInstID))
		{
			wi.setCurrentState((short)WorkItemStatus.Suspended.getValue());
			workflowPersistence.UpdateWorkItem(wi);
		}

		workflowPersistence.UpdateActivityInstStatus(activityInstID, ActivityInstStatus.Suspended);
	}

	/** 
	 恢复活动实例
	 
	 @param activityInstID 活动实例ID
	*/
	public final void ResumeActivityInst(String activityInstID)
	{
		for (WorkItem wi : workflowPersistence.GetWorkItems(activityInstID))
		{
			wi.setCurrentState((short)WorkItemStatus.WaitExecute.getValue());
			workflowPersistence.UpdateWorkItem(wi);
		}

		workflowPersistence.UpdateActivityInstStatus(activityInstID, ActivityInstStatus.Running);
	}

	/** 
	 激活活动实例
	 
	 @param activityInstID 活动实例ID
	*/
	public final void ActivateActivityInst(String activityInstID)
	{
		for (WorkItem wi : workflowPersistence.GetWorkItems(activityInstID))
		{
			wi.setCurrentState((short)WorkItemStatus.WaitExecute.getValue());
			workflowPersistence.UpdateWorkItem(wi);
		}

		workflowPersistence.UpdateActivityInstStatus(activityInstID, ActivityInstStatus.Running);
	}

	/** 
	 终止活动实例
	 
	 @param activityInstID 活动实例ID
	*/
	public final void TerminateActivityInst(String activityInstID)
	{
		for (WorkItem wi : workflowPersistence.GetWorkItems(activityInstID))
		{
			wi.setCurrentState((short)WorkItemStatus.Terminated.getValue());
			workflowPersistence.UpdateWorkItem(wi);
		}

		workflowPersistence.UpdateActivityInstStatus(activityInstID, ActivityInstStatus.Terminated);
	}

	/** 
	 将活动实例置为出错
	 
	 @param activityInstID 活动实例ID
	*/
	public final void ErrorActivityInst(String activityInstID)
	{
		for (WorkItem wi : workflowPersistence.GetWorkItems(activityInstID))
		{
			wi.setCurrentState((short)WorkItemStatus.Error.getValue());
			workflowPersistence.UpdateWorkItem(wi);
		}

		workflowPersistence.UpdateActivityInstStatus(activityInstID, ActivityInstStatus.Error);
	}

	/** 
	 获取待办工作项列表
	 
	 @param userId 用户ID
	 @param parameters 参数
	 @param pageInfo 分页信息
	 @param includeAuto 是否包括自动活动产生的工作项信息
	 @return 
	*/
	public final PageDataResult GetMyWorkItems(String userId, Map<String, Object> parameters, String orderby, RequestPageData pageInfo, boolean includeAuto)
	{
		return workflowPersistence.GetMyWorkItems(userId, parameters, orderby, pageInfo, includeAuto);
	}

	/** 
	 完成一个工作项
	 
	 @param workItemID 工作项ID
	 @param parameters 参数
	*/
	public final void CompleteWorkItem(String workItemID, Map<String, Object> parameters, IAssignParticipant assignParticipant)
	{
		WorkItem wi = workflowPersistence.GetWorkItem(workItemID);
		if (wi.getCurrentState() != (short)WorkItemStatus.WaitExecute.getValue() && wi.getCurrentState() != (short)WorkItemStatus.Suspended.getValue())
		{
			WorkflowException workflowException = new WorkflowException(String.format("ID=%1$s的工作项%2$s当前状态=%3$s,不能被执行", wi.getId(), wi.getName(),WorkItemStatus.forValue(wi.getCurrentState())).toString());
			WFUtil.HandleException(workflowException);
			return;
		}

		wi.setCurrentState((short)WorkItemStatus.Executing.getValue());
		workflowPersistence.UpdateWorkItem(wi);
		workflowPersistence.UpdateActivityInstStatus(wi.getActivityInstID(), ActivityInstStatus.Running);

		ProcessInst processInst = workflowPersistence.GetProcessInst(wi.getProcessInstID());
		ProcessDefine processDefine = workflowPersistence.GetProcessDefine(processInst.getProcessDefID());
		ProcessContext processContext = new ProcessContext(processDefine,processInst,parameters);

		ProcessRuntime runtime = new ProcessRuntime(workflowPersistence, processContext, assignParticipant);
		runtime.Run();
	}


	/** 
	 委托代办工作项
	 
	 @param user 当前用户
	 @param agentID 代理用户
	 @param workItemID 工作项ID
	*/
	public final void DelegateWorkItem(ShiroUser user, String agentID, String workItemID)
	{

	}

	/** 
	 删除工作项
	 
	 @param workItemID 工作项ID
	*/
	public final void DeleteWorkItem(String workItemID)
	{
		workflowPersistence.DeleteWorkItem(workItemID);
	}

	/** 
	 代办工作项
	 
	 @param user 当前用户
	 @param workItemID 工作项ID
	*/
	public final void AgentWorkItem(ShiroUser user, String workItemID)
	{
		//WorkItem wi = repository.GetDomain<WorkItem>(workItemID);

		//repository.GetDomain
	}
	/** 
	 领取工作项
	 
	 @param user 当前用户
	 @param workItemID 工作项ID
	*/
	public final void FetchWorkItem(ShiroUser user, String workItemID)
	{
		workflowPersistence.UpdateWorkItemStatus(workItemID, WorkItemStatus.Executing);
	}

	/** 
	 获取当前流程实例待执行工作项及参与者字典
	 
	 @param processInstID 流程实例ID
	 @return 
	*/
	public final Map<WorkItem, List<Operator>> GetActiveWorkItems(String processInstID)
	{
		Map<WorkItem, List<Operator>> result = new HashMap<WorkItem, List<Operator>>();
		
		Map<String,Object> parameters=new HashMap<String,Object>();
		parameters.put("processInstID", processInstID);
		parameters.put("currentState", (short)WorkItemStatus.WaitExecute.getValue());
		
		List<WorkItem> activeWorkItems =workflowPersistence.getWorkItems(parameters);
		for (WorkItem item : activeWorkItems)
		{
			List<Operator> participants= workflowPersistence.GetWorkItemParticipant(item.getId());
			result.put(item, participants);
		}
		return result;
	}
	
	/** 
	 退回工作项
	 说明：将当前工作项退回到，最近一个可退回的活动。
	 最近可退回活动:最近可退回活动，是当前活动的主支上最近的一个共同祖先结点。
	 具体步骤：
	 1、寻找最近的一个可退回目标活动。
	 2、将流程回退到该目标活动。
	 
	 @param user 当前用户
	 @param workItemID 工作项ID
	*/
	public final void RollbackWorkItem(ShiroUser user, String workItemID)
	{
		WorkItem wi = workflowPersistence.GetWorkItem(workItemID);
		if (wi.getCurrentState() != (short)WorkItemStatus.Executing.getValue())
		{
			ActivityInst actInst = workflowPersistence.GetActivityInst(wi.getActivityInstID());
			//寻找最近的一个可退回活动。
			Activity destActivity = GetFirstRollbackActivity(wi.getProcessID(), actInst.getActivityDefID());

			if (destActivity.getActivityType() != ActivityType.StartActivity)
			{
				logger.info(String.format("回退工作项%1$s-%2$s到活动%3$s-%4$s", wi.getId(), wi.getName(), destActivity.getID(), destActivity.getName()));
				//回退到目标活动
				RollbackActivity(user, actInst, destActivity);
			}

			return;
		}

		WorkflowException workflowException = new WorkflowException(String.format("ID=%1$s的工作项%2$s当前状态=%3$s,不能被执行", wi.getId(), wi.getName(), WorkItemStatus.forValue(wi.getCurrentState())));
		WFUtil.HandleException(workflowException);
	}

	/** 
	 获取活动第一个分支祖先活动
	 
	 @param processDefID
	 @param activity
	 @return 
	*/
	private Activity GetForefatherSplitActivity(String processDefID, Activity activity)
	{
		List<Activity> inActivities = workflowPersistence.GetInActivities(processDefID, activity.getID());

		if (inActivities.size() > 1 || (inActivities.size() == 1 && inActivities.get(0).getActivityType() == ActivityType.StartActivity))
		{
			return activity;
		}

		return GetForefatherSplitActivity(processDefID, inActivities.get(0));
	}

	/** 
	 获取第一个共同祖先结点
	 
	 @param processDefine 流程定义
	 @param splitActivites 分支结点列表
	 @return 
	*/
	private Activity GetFirstRollbackActivity(String processDefID, String activityID)
	{
		List<Activity> inActivities = workflowPersistence.GetInActivities(processDefID, activityID);
		if (inActivities == null)
		{
			return null;
		}

		if (inActivities.size() == 1)
		{
			return inActivities.get(0);
		}

		//获取所有入口活动的第一个共同祖先结点
		return GetForefatherActivity(processDefID, inActivities);
	}

	/** 
	 获取第一个共同祖先结点
	 
	 @param processDefine 流程定义
	 @param splitActivites 分支结点列表
	 @return 
	*/
	private Activity GetForefatherActivity(String processDefID, List<Activity> splitActivites)
	{
		if (splitActivites != null && splitActivites.size() == 1)
		{
			return splitActivites.get(0);
		}

		int count = splitActivites.size();
		Activity splitParent1 = GetForefatherSplitActivity(processDefID, splitActivites.get(count - 1));
		Activity splitParent2 = GetForefatherSplitActivity(processDefID, splitActivites.get(count - 2));
		Activity joinParent = null;
		if (splitParent1.getID().equals(splitParent2.getID()) && splitActivites.size() == 2)
		{
			return splitParent1;
		}
		else if (!splitParent1.getID().equals(splitParent2.getID()))
		{
			List<Activity> children = new java.util.ArrayList<Activity>(java.util.Arrays.asList(new Activity[] { splitParent1, splitParent2 }));
			joinParent = GetForefatherActivity(processDefID, children);
		}
		else
		{
			joinParent = splitParent1;
		}

		splitActivites.remove(count - 1);
		splitActivites.remove(count - 2);
		splitActivites.add(joinParent);

		return GetForefatherActivity(processDefID, splitActivites);
	}

	/** 
	 获取当前活动，可回退的活动列表
	 
	 @param processDefID 流程定义ID
	 @param activityID 当前活动ID
	 @return 
	*/
	public final List<Activity> GetRollbackActivityList(String processDefID, String activityID)
	{
		List<Activity> result = new java.util.ArrayList<Activity>();
		List<Activity> inActivities = workflowPersistence.GetInActivities(processDefID, activityID);
		if (inActivities == null)
		{
			return null;
		}

		if (inActivities.size() == 1)
		{
			if (inActivities.get(0).getActivityType() != ActivityType.StartActivity)
			{
				result.addAll(inActivities);
				result.addAll(GetRollbackActivityList(processDefID, inActivities.get(0).getID()));
			}
			return result;
		}

		//获取所有入口活动的第一个共同祖先结点
		Activity forefather = GetForefatherActivity(processDefID, inActivities);
		result.add(forefather);

		result.addAll(GetRollbackActivityList(processDefID, forefather.getID()));

		return result;
	}

	/** 
	 终点某活动的自身及所有后继活动实例
	 
	 @param processInstID 流程实例ID
	 @param destActivity 目标活动
	*/
	private void TerminateSelfAndOutActivity(String processInstID, Activity destActivity)
	{
		ActivityInst destActInst= workflowPersistence.GetActivityInsts(processInstID, destActivity.getID()).get(0);
		
		if (destActInst != null)
		{
			//var outActInsts = repository.<ActivityInst>FindAll(ParameterBuilder.BuildParameters().SafeAdd("ID", new Condition(String.format("ID in(select DestActInstID from WF_Transition where SrcActInstID='%1$s')", destActInst.ID))).SafeAdd("RollbackFlag", 0));
			Map<String,Object> parameters=new HashMap<String,Object>();
			parameters.put("rollbackFlag", (short)0);
			parameters.put("_rawsql", String.format("ID in(select DestActInstID from WF_Transition where SrcActInstID='%1$s')", destActInst.getId()));
			List<ActivityInst> outActInsts=	workflowPersistence.ActivityInsts(parameters);
			
			//终止工作项
			//终止当前活动
			//继续终止该活动的出口活动
			if (outActInsts != null)
			{
				for (ActivityInst actInst : outActInsts)
				{
					List<WorkItem> workItems = workflowPersistence.GetWorkItems(actInst.getId());
					for (WorkItem wi : workItems)
					{
						wi.setCurrentState((short)WorkItemStatus.Terminated.getValue());
						logger.info(String.format("终止工作项%1$s-%2$s", wi.getId(), wi.getName()));
						workflowPersistence.UpdateWorkItem(wi);
					}
					actInst.setCurrentState((short)ActivityInstStatus.Terminated.getValue());
					actInst.setRollbackFlag((short)1);
					logger.info(String.format("终止活动实例%1$s-%2$s", actInst.getId(), actInst.getName()));
					
					workflowPersistence.UpdateActivityInst(actInst);
					ProcessInst processInst =workflowPersistence.GetProcessInst(processInstID);
					Activity newDestAct = workflowPersistence.GetActivity(processInst.getProcessDefID(), actInst.getActivityDefID());
					TerminateSelfAndOutActivity(processInstID, newDestAct);
				}
			}
			destActInst.setRollbackFlag((short)1);
			destActInst.setCurrentState((short)ActivityInstStatus.Terminated.getValue());
			logger.info(String.format("终止活动实例%1$s-%2$s", destActInst.getId(), destActInst.getName()));
			workflowPersistence.UpdateActivityInst(destActInst);
		}
	}

	/** 
	 回退流程到某个活动
	 说明：将当前活动实例回退到，某个目标活动。
	 具体步骤：
	 1、寻找目标活动实例。
	 2、将目标活动及后续已完成的活动实例及工作项终止。
	 3、终止当前活动实例及工作项。（注：为记录当前执行人再终止一次）
	 4、重新启动可退回活动。
	 
	 @param user
	 @param srcActInst
	 @param destActivity
	*/
	public final void RollbackActivity(ShiroUser user, ActivityInst srcActInst, Activity destActivity)
	{
		ActivityInst destActInst= workflowPersistence.GetActivityInsts(srcActInst.getProcessInstID(), destActivity.getID()).get(0);
		
		if (destActInst != null)
		{
			java.util.Date createTime = new java.util.Date();
			//终止destActivity所有的后续已执行的的活动实例
			//将当前活动实例的工作项终止（将终止人设为当前用户)
			//将当前活动实例终止
			//将目标活动实例的工作项重置为待执行
				//wi.ExecutorName = user.Name;
				//wi.Executor = user.ID;
				// wi.ExecuteTime = null;// DateTime.MaxValue;
			//将目标活动实例状态置为未开始
			TerminateSelfAndOutActivity(destActInst.getProcessInstID(), destActivity);
			List<WorkItem> workItems = workflowPersistence.GetWorkItems(srcActInst.getId());
			for (WorkItem wi : workItems)
			{
				wi.setCurrentState((short)WorkItemStatus.Terminated.getValue());
				wi.setExecutorName( user.getName());
				wi.setExecutor(user.getId());
				wi.setExecuteTime( createTime);
				logger.info(String.format("终止工作项%1$s-%2$s", wi.getId(), wi.getName()));
				workflowPersistence.UpdateWorkItem(wi);
			}
			srcActInst.setCurrentState((short)ActivityInstStatus.Terminated.getValue());
			srcActInst.setRollbackFlag((short)1);
			srcActInst.setEndTime(createTime);
			
			logger.info(String.format("终止活动实例%1$s-%2$s", srcActInst.getId(), srcActInst.getName()));
			workflowPersistence.SaveOrUpdateActivityInst(srcActInst);
			workItems = workflowPersistence.GetWorkItems(destActInst.getId());
			for (WorkItem wi : workItems)
			{
				wi.setCurrentState((short)WorkItemStatus.WaitExecute.getValue());
				logger.info(String.format("启动工作项%1$s-%2$s", wi.getId(), wi.getName()));
				workflowPersistence.UpdateWorkItem(wi);
			}
			destActInst.setCurrentState((short)ActivityInstStatus.NoStart.getValue());
			destActInst.setRollbackFlag((short)0);
			
			logger.info(String.format("启动活动实例%1$s-%2$s", destActInst.getId(), destActInst.getName()));
			workflowPersistence.UpdateActivityInst(destActInst);
			
			ProcessInst processInst =workflowPersistence.GetProcessInst(srcActInst.getProcessInstID());
			Transition transition = new Transition();
			transition.setId(Identities.uuid());
			transition.setDestActID(destActivity.getID());
			transition.setDestActInstID(destActInst.getId());
			transition.setDestActInstName(destActInst.getName());
			transition.setDestActName(destActivity.getName());
			transition.setProcessInstID(srcActInst.getProcessInstID());
			transition.setProcessInstName(processInst.getName());
			transition.setSrcActID(srcActInst.getActivityDefID());
			transition.setSrcActInstID(srcActInst.getId());
			transition.setSrcActInstName(srcActInst.getName());
			transition.setSrcActName(srcActInst.getName());
			transition.setTransTime(createTime);
			workflowPersistence.SaveTransition(transition);
			
			TransControl transControl = new TransControl();
			transControl.setId(Identities.uuid());
			transControl.setDestActID(destActivity.getID());
			transControl.setDestActName(destActivity.getName());
			transControl.setProcessInstID(srcActInst.getProcessInstID());
			transControl.setSrcActID(srcActInst.getActivityDefID());
			transControl.setSrcActName(srcActInst.getName());
			transControl.setTransTime(createTime);
			transControl.setTransWeight(100);
			workflowPersistence.SaveTransControl(transControl);
		}
	}

	public final void SkipToWorkItem(ShiroUser user, String workItemID)
	{
		throw new WorkflowException("NotImplementedException");
	}

	public final List<Activity> GetSkipActivityList(ShiroUser user, Activity srcAct)
	{
		throw new WorkflowException("NotImplementedException");
	}

	public final void SkipToActivity(ShiroUser user, ActivityInst srcActInst, Activity destActivity)
	{
		throw new WorkflowException("NotImplementedException");
	}
	
	/** 
	 获取角色和组织参与者
	 
	 @return 
	*/
	public final List<Participantor> GetRoleAndOrgParticipantors()
	{
		return participantorService.getRoleAndOrgParticipantors();
	}

	/** 
	 获取某参与者类型下的所有参与者
	 
	 @param parentType 父参与者类型
	 @param parentID 父参与者ID
	 @return 
	*/
	public final List<Participantor> GetPersonParticipantors(ParticipantorType parentType, String parentID)
	{
		return participantorService.getPersonParticipantors(parentType,parentID);
	}
}