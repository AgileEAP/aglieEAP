package com.agileEAP.workflow.engine;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
 
import org.apache.commons.lang3.StringUtils;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.Bindings; 
import javax.script.ScriptException;

import org.springframework.transaction.annotation.Transactional;

import com.agileEAP.utils.Identities;
import com.agileEAP.workflow.entity.*;
import com.agileEAP.workflow.engine.exception.WorkflowException;
import com.agileEAP.workflow.engine.enums.*;
import com.agileEAP.workflow.engine.utility.WFUtil;
import com.agileEAP.workflow.definition.*;

/** 
 实现推动和更改具体某个Workflow状态，要调用ProcessRuntime
 
*/
@Transactional
public class ProcessRuntime
{
	private IWorkflowPersistence workflowPersistence;
	private IAssignParticipant assignParticipant = null;
	private boolean waiting = false;

	private IWorkflowPersistence getPersistence()
	{
		return workflowPersistence;
	}

	private ProcessContext context;
	public final ProcessContext getContext()
	{
		return context;
	}
	public final void setContext(ProcessContext value)
	{
		context = value;
	}

	public ProcessRuntime(IWorkflowPersistence workflowPersistence, ProcessContext context, IAssignParticipant assignParticipant)
	{
		this.workflowPersistence = workflowPersistence;
		this.context=context;
		this.assignParticipant = assignParticipant;
	}

	public final void Run()
	{
		waiting = true;
		while (waiting)
		{
			waiting = false;
			
			//var runningActInsts = getPersistence().getRepository().<ActivityInst>Query().Where(ai => ai.ProcessInstID == getContext().getProcessInst().ID && ai.CurrentState == (short)ActivityInstStatus.Running.getValue() && ai.RollbackFlag == 0).toArray();
			List<ActivityInst> runningActInsts=getPersistence().GetRunningActInsts(getContext().getProcessInst().getId());
			for (ActivityInst activityInst : runningActInsts)
			{
				ActivityContext activityContext = new ActivityContext();
				activityContext.setProcessInst(getContext().getProcessInst());
				activityContext.setActivityInst(activityInst);
				activityContext.setParameters(getContext().getParameters());
				activityContext.setProcessDefine(getContext().getProcessDefine());
				ActivityRuntime runtime = new ActivityRuntime(workflowPersistence, activityContext);

				runtime.Run();

				UpdateActivityInstAndRoute(activityInst);
			}
		}

		CheckAndUpdateProcessInstStatus();
	}

	private void UpdateActivityInstAndRoute(ActivityInst activityInst)
	{
		List<WorkItem> workItems = getPersistence().GetWorkItems(activityInst.getId());
		ActivityInstStatus newStatus = ActivityInstStatus.NoStart;

		boolean allCompleted = true;
		for (WorkItem wi : workItems)
		{
			switch (WorkItemStatus.forValue((int)wi.getCurrentState()))
			{
				case WaitExecute:
					allCompleted = false;
					break;
				case Suspended:
					allCompleted = false;
					break;
				case Executing:
					allCompleted = false;
					if (newStatus == ActivityInstStatus.NoStart)
					{
						newStatus = ActivityInstStatus.Running;
					}
					break;
				case Compeleted:
					if (newStatus == ActivityInstStatus.NoStart ||newStatus == ActivityInstStatus.Running)
					{
						newStatus = ActivityInstStatus.Running;
					}
					break;
				case Canceled:
					break;
				case Terminated:
					break;
			default:
				break;
			}
		}

		if (allCompleted)
		{
			newStatus = ActivityInstStatus.Compeleted;
		}

		if (activityInst.getCurrentState() != (short)newStatus.getValue())
		{
			activityInst.setCurrentState((short)newStatus.getValue());
			getPersistence().UpdateActivityInstStatus(activityInst.getId(), newStatus);
			waiting = true;
		}
		if (allCompleted)
		{
			newStatus = ActivityInstStatus.Compeleted;
			RouteActivityInst(activityInst);
		}
	}

	/** 
	 检测活动是否可以激活
	 
	 @param processInstID
	 @param activity
	 @return 
	*/
	@Transactional
	public final boolean CanActivateActiviy(String processInstID, Activity activity)
	{
		List<ActivityInst> actInsts = getPersistence().GetActivityInsts(processInstID, activity.getID());
		if (actInsts != null && actInsts.size() > 0)
		{
			return false;
		}

		List<Activity> activities = getPersistence().GetInActivities(getContext().getProcessInst().getProcessDefID(), activity.getID());
		for (Activity act : activities)
		{
			if (activity.getJoinType() == JoinType.XOR)
			{
				boolean hasCompleted = false;
				for (ActivityInst actInst : getPersistence().GetActivityInsts(processInstID, act.getID()))
				{
					if ((int)actInst.getCurrentState() == ActivityInstStatus.Compeleted.getValue())
					{
						hasCompleted = true;
						break;
					}
				}

				if (hasCompleted)
				{
					for (ActivityInst actInst : getPersistence().GetActivityInsts(processInstID, act.getID()))
					{
						if (actInst.getCurrentState() != (short)ActivityInstStatus.Compeleted.getValue() && actInst.getCurrentState() != (short)ActivityInstStatus.Terminated.getValue())
						{
							for (WorkItem workItem : getPersistence().GetWorkItems(actInst.getId()))
							{
								if (workItem.getCurrentState() != (short)WorkItemStatus.Compeleted.getValue() && actInst.getCurrentState() != (short)ActivityInstStatus.Terminated.getValue())
								{
									workItem.setCurrentState((short)WorkItemStatus.Terminated.getValue());
									
									getPersistence().UpdateWorkItem(workItem);
								}
							}

							actInst.setCurrentState((short)ActivityInstStatus.Terminated.getValue());
							getPersistence().UpdateActivityInst(actInst);
						}
					}
				}

				return hasCompleted;
			}
			else
			{
				for (ActivityInst actInst : getPersistence().GetActivityInsts(processInstID, act.getID()))
				{
					if (actInst.getCurrentState() != (short)ActivityInstStatus.Compeleted.getValue() && actInst.getCurrentState() != (short)ActivityInstStatus.Terminated.getValue())
					{
						return false;
					}
				}
			}
		}

		return true;
	}

	/** 
	 获取待运行活动
	 
	 @param processDefID
	 @param srcActInst
	 @return 
	*/
	public final List<Activity> GetActivateActivities(String processDefID, ActivityInst srcActInst)
	{
		List<com.agileEAP.workflow.definition.Transition> transitions = getPersistence().GetOutTransitions(processDefID, srcActInst.getActivityDefID());
		Activity srcActDef = getPersistence().GetActivity(processDefID, srcActInst.getActivityDefID());

		List<Activity> activateActivities = new java.util.ArrayList<Activity>();
		Activity defaultActivity = null;
		for (com.agileEAP.workflow.definition.Transition transition : transitions)
		{
			//IDictionary<string, object> parameters = new Dictionary<string, object>();
			//parameters.SafeAdd("money", 1000);
			//parameters.SafeAdd("number", 4);
			//Context.Parameters = parameters;
			////engine.SetParameter(":money", 1000);
			//transition.Expression = "return @money-100>0&&@number<10;";
			//new Regex(@"[^@@](?<p>@\w+)");对@@value,不表示变量;Regex(@"@\w*")以@开头的表示变量。
			String expression = transition.getExpression();

			boolean expressionResult = expression==null||expression.length()==0;
			if (!expressionResult)
			{
				char prefix = WFUtil.ExpressionVariablePrefix;
/*					Regex regex = new Regex(String.format("%1$s\\w*", prefix));
					MatchCollection matches = regex.Matches(expression.replace(String.format("%1$s%1$s", prefix), "###"));
					JintEngine engine = new JintEngine();
					if (matches != null && getContext().getParameters() != null)
					{
						for (Match match : matches)
						{
							String variable = match.getValue().TrimStart(prefix);
							if (getContext().getParameters().containsKey(variable))
							{
								engine.SetParameter(variable, getContext().getParameters().get(variable));
							}
						}
					}
					expression = expression.replace(String.format("%1$s%1$s", prefix), "###").Replace((new Character(prefix)).toString(), "").Replace("###", (new Character(prefix)).toString());
					expressionResult = (boolean)engine.Run(expression);
					*/
				Pattern pattern =Pattern.compile(String.format("%1$s\\w*", prefix));
				Matcher matcher=pattern.matcher(expression.replace(String.format("%1$s%1$s", prefix), "###"));
				ScriptEngineManager manager = new ScriptEngineManager();  
				ScriptEngine engine = manager.getEngineByName("js");  
				    
				while (matcher.find()) {
					
					String variable = StringUtils.stripStart(matcher.group(), String.valueOf(prefix));
					if (getContext().getParameters().containsKey(variable))
					{
						engine.put(variable, getContext().getParameters().get(variable));
					}
				}

				expression = expression.replace(String.format("%1$s%1$s", prefix), "###").replace((new Character(prefix)).toString(), "").replace("###", String.valueOf(prefix));
				try {
					expressionResult = (boolean)engine.eval(expression);
				} catch (ScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					WorkflowException ex=	new WorkflowException(String.format("计算表达式%1$s出错", expression), e);
					WFUtil.HandleException(ex);
					throw ex;
				}
			}

			Activity destActivity = getPersistence().GetActivity(processDefID, transition.getDestActivity());
			//记住默认活动
			if (transition.getIsDefault())
			{
				defaultActivity = destActivity;
			}

			if (expressionResult && CanActivateActiviy(srcActInst.getProcessInstID(), destActivity))
			{
				activateActivities.add(destActivity);
			}
		}

		if (activateActivities.isEmpty() && defaultActivity != null && CanActivateActiviy(srcActInst.getProcessInstID(), defaultActivity))
		{
			activateActivities.add(defaultActivity);
		}

		return activateActivities;
	}

	/** 
	 产生迁移活动
	 
	 @param srcActInst
	*/
	public final void RouteActivityInst(ActivityInst srcActInst)
	{
		Activity srcActivity =WFUtil.getActivity(getContext().getProcessDefine().getActivities(), srcActInst.getActivityDefID());
		List<Activity> activateActivities = GetActivateActivities(getContext().getProcessInst().getProcessDefID(), srcActInst);
		String processInstID = getContext().getProcessInst().getId();
		String processDefID = getContext().getProcessInst().getProcessDefID();

		for (Activity destActivity : activateActivities)
		{
			java.util.Date createTime = new java.util.Date();
			ActivityInst destActInst = new ActivityInst();
			destActInst.setActivityDefID(destActivity.getID());
			destActInst.setCurrentState((short)(destActivity.getActivityType() == ActivityType.EndActivity || destActivity.getActivityType() == ActivityType.AutoActivity ? ActivityInstStatus.Compeleted.getValue() : ActivityInstStatus.NoStart.getValue()));
			destActInst.setId(Identities.uuid());
			destActInst.setCreateTime(createTime);
			destActInst.setDescription(destActivity.getDescription());
			destActInst.setEndTime(WFUtil.MaxDate);
			destActInst.setName(destActivity.getName());
			destActInst.setProcessInstID(processInstID);
			destActInst.setRollbackFlag((short)0);
			destActInst.setStartTime(createTime);
			destActInst.setSubProcessInstID("");
			destActInst.setType((short)destActivity.getActivityType().getValue());
			
			com.agileEAP.workflow.entity.Transition transition = new com.agileEAP.workflow.entity.Transition();
			transition.setId(Identities.uuid());
			transition.setDestActID(destActivity.getID());
			transition.setDestActInstID(destActInst.getId());
			transition.setDestActInstName(destActInst.getName());
			transition.setDestActName(destActivity.getName());
			transition.setProcessInstID(processInstID);
			transition.setProcessInstName(getContext().getProcessInst().getName());
			transition.setSrcActID(srcActivity.getID());
			transition.setSrcActInstID(srcActInst.getId());
			transition.setSrcActInstName(srcActInst.getName());
			transition.setSrcActName(srcActivity.getName());
			transition.setTransTime(createTime);
			
			TransControl transControl = new TransControl();
			transControl.setId(Identities.uuid());
			transControl.setDestActID(destActivity.getID());
			transControl.setDestActName(destActivity.getName());
			transControl.setProcessInstID(processInstID);
			transControl.setSrcActID(srcActivity.getID());
			transControl.setSrcActName(srcActivity.getName());
			transControl.setTransTime(createTime);
			transControl.setTransWeight(100);
			
			if (destActivity instanceof ManualActivity)
			{
				ManualActivity ma = (ManualActivity)((destActivity instanceof ManualActivity) ? destActivity : null);
				WorkItem wi = new WorkItem();
				wi.setId(Identities.uuid());
				wi.setActionMask("");
				wi.setActionURL(ma.getCustomURL().getSpecifyURL());
				wi.setActivityInstID(destActInst.getId());
				wi.setActivityInstName(destActInst.getName());
				wi.setAllowAgent((short)(ma.getAllowAgent() ? 1 : 0));
				wi.setBizState((short)WorkItemBizStatus.Common.getValue());
				wi.setCreateTime(createTime);
				wi.setCreator(WFUtil.getUser().getId());
				wi.setCreatorName(WFUtil.getUser().getName());
				wi.setCurrentState((short)WorkItemStatus.WaitExecute.getValue());
				wi.setDescription(destActInst.getDescription());
				wi.setEndTime(WFUtil.MaxDate);
				wi.setExecutor("");
				wi.setExecutorName("");
				wi.setIsTimeOut((short)0);
				wi.setName(destActInst.getName());
				wi.setParticipant(getPersistence().GetParticipant(processDefID, destActivity.getID()));
				wi.setProcessID(processDefID);
				wi.setProcessInstID(processInstID);
				wi.setProcessInstName(getContext().getProcessInst().getName());
				wi.setProcessName(getContext().getProcessDefine().getName());
				wi.setRemindTime(WFUtil.MaxDate);
				wi.setRootProcessInstID("");
				wi.setStartTime(createTime);
				wi.setTimeOutTime(WFUtil.MaxDate);
				wi.setType(destActInst.getType());
				
				List<Participantor> participantors = assignParticipant == null ? ma.getParticipant().getParticipantors() : new java.util.ArrayList<Participantor>(java.util.Arrays.asList(new Participantor[] { assignParticipant.AssignParticipant() }));
				for (Participantor participantor : participantors)
				{
					com.agileEAP.workflow.entity.Participant participant = new com.agileEAP.workflow.entity.Participant();
					participant.setId(Identities.uuid());
					participant.setCreateTime(createTime);
					participant.setDelegateType((short)DelegateType.Sponsor.getValue());
					participant.setName(participantor.getName());
					participant.setParticipantID(participantor.getID());
					participant.setParticipantIndex(participantor.getSortOrder());
					participant.setParticipantType((short)participantor.getParticipantorType().getValue());
					participant.setPartiInType((short)PartiInType.Exe.getValue());
					participant.setWorkItemID(wi.getId());
					participant.setWorkItemState((short)wi.getCurrentState());
					getPersistence().SaveParticipant(participant);
				}
				getPersistence().SaveWorkItem(wi);
			}
			else if (destActivity instanceof AutoActivity)
			{
				if (!HandlerAutoActivity(processDefID, srcActivity, destActivity, destActInst))
				{
					destActInst.setCurrentState((short)ActivityInstStatus.Suspended.getValue());
				}
			}
			srcActInst.setCurrentState((short)ActivityInstStatus.Compeleted.getValue());
			getPersistence().SaveOrUpdateActivityInst(srcActInst);
			getPersistence().SaveOrUpdateActivityInst(destActInst);
			getPersistence().SaveTransition(transition);
			getPersistence().SaveTransControl(transControl);
		}
	}

	private boolean HandlerAutoActivity(String processDefID, Activity srcActivity, Activity destActivity, ActivityInst destActInst)
	{
		java.util.Date createTime = new java.util.Date();
		AutoActivity autoActivity = (AutoActivity)((destActivity instanceof AutoActivity) ? destActivity : null);
		ManualActivity manualActivity = (ManualActivity)((srcActivity instanceof ManualActivity) ? srcActivity : null);
		String actionURL = null;
		if (manualActivity != null && manualActivity.getCustomURL() != null)
		{
			actionURL = manualActivity.getCustomURL().getSpecifyURL();
		}

		WorkItem wi = new WorkItem();
		wi.setId(Identities.uuid());
		wi.setActionMask("");
		wi.setActivityInstID(destActInst.getId());
		wi.setActivityInstName(destActInst.getName());
		wi.setBizState((short)WorkItemBizStatus.Common.getValue());
		wi.setCreateTime(createTime);
		wi.setCreator(WFUtil.getUser().getId());
		wi.setCreatorName(WFUtil.getUser().getName());
		wi.setCurrentState((short)WorkItemStatus.Executing.getValue());
		wi.setDescription(destActInst.getDescription());
		wi.setEndTime(WFUtil.MaxDate);
		wi.setExecutor("");
		wi.setExecutorName("");
		wi.setIsTimeOut((short)0);
		wi.setName(destActInst.getName());
		wi.setParticipant("com.agileEAP.workflow");
		wi.setProcessID(processDefID);
		wi.setProcessInstID(destActInst.getProcessInstID());
		wi.setProcessInstName(getContext().getProcessInst().getName());
		wi.setProcessName(getContext().getProcessDefine().getName());
		wi.setRemindTime(WFUtil.MaxDate);
		wi.setRootProcessInstID("");
		wi.setStartTime(createTime);
		wi.setTimeOutTime(WFUtil.MaxDate);
		wi.setType(destActInst.getType());
		wi.setActionURL(actionURL);

		boolean isCompeleted = false;
		//如果自动处理完成并返回true,完成工作项，并路由到下一活动
		if (DefaultExecutor.Execute(String.format("%1$s-%2$s-%3$s", getContext().getProcessDefine().getID(), getContext().getProcessDefine().getVersion(), destActivity.getID()), wi))
		{
			wi.setCurrentState((short)WorkItemStatus.Compeleted.getValue());
			getPersistence().SaveWorkItem(wi);
			RouteActivityInst(destActInst);
			isCompeleted = true;
		}
		else
		{
			wi.setCurrentState((short)WorkItemStatus.Suspended.getValue());
			getPersistence().SaveWorkItem(wi);
			isCompeleted = false;
		}

		return isCompeleted;
	}

	private void CheckAndUpdateProcessInstStatus()
	{
		//TODO: 还需要考虑其他条件和状态(Cancelled? Failed?)来判断Workflow的状态
		//例如Workflow是否Complete了，该如何判断？所有的Activity都完成了？最后的Activity完成了？

		ProcessInstStatus newStatus = ProcessInstStatus.NoStart;
		List<ActivityInst> activityInsts = getPersistence().GetActivityInsts(getContext().getProcessInst().getId());
		boolean allCompleted = true;
		for (ActivityInst ai : activityInsts)
		{
			switch (ActivityInstStatus.forValue((int)ai.getCurrentState()))
			{
				case NoStart:
					allCompleted = false;
					break;
				case Suspended:
					allCompleted = false;
					newStatus = ProcessInstStatus.Running;
					break;
				case Running:
					allCompleted = false;
					if (newStatus == ProcessInstStatus.NoStart)
					{
						newStatus = ProcessInstStatus.Running;
					}
					break;
				//case ActivityInstStatus.In_Progress:
				//    allCompleted = false;
				//    if (newStatus == ProcessInstStatus.NotStarted || newStatus == ProcessInstStatus.In_Progress)
				//        newStatus = ProcessInstStatus.In_Progress;
				//    break;
				case Compeleted:
					if (newStatus == ProcessInstStatus.NoStart || newStatus == ProcessInstStatus.Running)
					{
						newStatus = ProcessInstStatus.Running;
					}
					break;
				//case ActivityInstStatus.Cancelled:
				//    break;
				case Error:
					break;
			default:
				break;
			}
		}
		if (allCompleted)
		{
			newStatus = ProcessInstStatus.Completed;
		}

		if (getContext().getProcessInst().getCurrentState() != (short)newStatus.getValue())
		{
			getContext().getProcessInst().setCurrentState((short)newStatus.getValue());
			getPersistence().UpdateProcessInstStatus(getContext().getProcessInst().getId(), newStatus);
		}
	}
}