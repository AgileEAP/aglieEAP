package com.agileEAP.workflow.engine;

import java.util.ArrayList;
import java.util.List;

import com.agileEAP.workflow.definition.*;
import com.agileEAP.workflow.engine.enums.ActivityInstStatus;
import com.agileEAP.workflow.engine.enums.Operation;
import com.agileEAP.workflow.engine.enums.WorkItemStatus;
import com.agileEAP.workflow.engine.exception.WorkflowException;
import com.agileEAP.workflow.engine.utility.WFUtil;
import com.agileEAP.workflow.entity.ActivityInst;
import com.agileEAP.workflow.entity.TraceLog;
import com.agileEAP.workflow.entity.WorkItem;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;
import com.agileEAP.utils.Identities;

public class WorkItemRuntime
{
	private IWorkflowPersistence workflowPersistence;

	public final IWorkflowPersistence getPersistence()
	{
		return workflowPersistence;
	}

	private ShiroUser user;
	public final ShiroUser getUser()
	{
		return user;
	}
	public final void setUser(ShiroUser value)
	{
		user = value;
	}

	private WorkItemContext context;
	public final WorkItemContext getContext()
	{
		return context;
	}
	public final void setContext(WorkItemContext value)
	{
		context = value;
	}

	public WorkItemRuntime(IWorkflowPersistence workflowPersistence, WorkItemContext context)
	{
		this.workflowPersistence = workflowPersistence;
		this.context=context;
	}

	public final void Run(Object state)
	{
		try
		{
			WorkItemExecutingEvent workItemExecutingEvent = new WorkItemExecutingEvent();
			workItemExecutingEvent.setUUID(String.format("%1$s-%2$s-%3$s-WorkItemExecutingEvent", getContext().getProcessDefine().getID(), getContext().getProcessDefine().getVersion(), getContext().getActivity().getID()));
			workItemExecutingEvent.setContext(getContext());
			EventPublisher.<WorkItemExecutingEvent>Publish(workItemExecutingEvent);

			CompleteWorkItem(WFUtil.getUser());

			WorkItemExecutedEvent workItemExecutedEvent = new WorkItemExecutedEvent();
			workItemExecutedEvent.setUUID(String.format("%1$s-%2$s-%3$s-WorkItemExecutedEvent", getContext().getProcessDefine().getID(), getContext().getProcessDefine().getVersion(), getContext().getActivity().getID()));
			workItemExecutedEvent.setContext(getContext());
			EventPublisher.<WorkItemExecutedEvent>Publish(workItemExecutedEvent);
		}
		catch (RuntimeException ex)
		{
			TraceLog traceLog = new TraceLog();
			traceLog.setActionType((short)Operation.Complete.getValue());
			traceLog.setActivityID(getContext().getActivityInst().getActivityDefID());
			traceLog.setActivityInstID(getContext().getActivityInst().getId());
			//traceLog.setClientIP(WebUtil.GetClientIP());
			traceLog.setId(Identities.uuid());
			traceLog.setOperator(WFUtil.getUser().getId());
			traceLog.setProcessID(getContext().getProcessInst().getProcessDefID());
			traceLog.setProcessInstID(getContext().getProcessInst().getId());
			traceLog.setWorkItemID(getContext().getWorkItem().getId());
			traceLog.setMessage(String.format("完成工作项%1$s出错,WorkItemID=%2$s", getContext().getWorkItem().getName(), getContext().getWorkItem().getId()));
			traceLog.setCreateTime(new java.util.Date());

			getPersistence().SaveTraceLog(traceLog);
			
			AbortWorkItem();
			WFUtil.HandleException(new WorkflowException(String.format("完成工作项%1$s出错,WorkItemID=%2$s", getContext().getWorkItem().getName(), getContext().getWorkItem().getId()), ex));

			WorkItemErrorEvent workItemErrorEvent = new WorkItemErrorEvent();
			workItemErrorEvent.setUUID(String.format("%1$s-%2$s-%3$s-WorkItemErrorEvent", getContext().getProcessDefine().getID(), getContext().getProcessDefine().getVersion(), getContext().getActivity().getID()));
			workItemErrorEvent.setContext(getContext());
			EventPublisher.<WorkItemErrorEvent>Publish(workItemErrorEvent);
		}
	}

	/** 
	 获取待执行活动
	 
	 @param processInstID 流程实例ID
	 @return 
	*/
	public final List<ActivityInst> GetToRunActivityInsts(String processInstID)
	{
		List<ActivityInst> toRunActivityInsts=new ArrayList<ActivityInst>();
		
		for(ActivityInst activityInst:getPersistence().GetActivityInsts(processInstID))
		{
			if(activityInst.getCurrentState() == (short)ActivityInstStatus.NoStart.getValue() || activityInst.getCurrentState() == (short)ActivityInstStatus.WaitActivate.getValue())
			{
				toRunActivityInsts.add(activityInst);
			}
		}
		
		return toRunActivityInsts;
	}

	/** 
	 完成工作项
	 
	*/
	public final void CompleteWorkItem(ShiroUser user)
	{
		WorkItem workItem=getContext().getWorkItem();
		workItem.setCurrentState((short)WorkItemStatus.Compeleted.getValue());
		workItem.setExecutor(WFUtil.getUser().getId());
		workItem.setExecutorName(WFUtil.getUser().getName());
		workItem.setExecuteTime(new java.util.Date());
		getPersistence().UpdateWorkItem(getContext().getWorkItem());

		TraceLog traceLog = new TraceLog();
		traceLog.setActionType((short)Operation.Complete.getValue());
		traceLog.setActivityID(getContext().getActivityInst().getActivityDefID());
		traceLog.setActivityInstID(getContext().getActivityInst().getId());
		//TODO tempVar.setClientIP(WebUtil.GetClientIP());
		traceLog.setId(Identities.uuid());
		traceLog.setOperator(WFUtil.getUser().getId());
		traceLog.setProcessID(getContext().getProcessInst().getProcessDefID());
		traceLog.setProcessInstID(getContext().getProcessInst().getId());
		traceLog.setWorkItemID(getContext().getWorkItem().getId());
		traceLog.setMessage(String.format("完成工作项%1$s", getContext().getWorkItem().getName()));
		traceLog.setCreateTime(new java.util.Date());
		getPersistence().SaveTraceLog(traceLog);

	}

	public final void AbortWorkItem()
	{
		WorkItem workItem=getContext().getWorkItem();
		workItem.setCurrentState((short)WorkItemStatus.Error.getValue());
		getPersistence().UpdateWorkItem(workItem);
	}
}