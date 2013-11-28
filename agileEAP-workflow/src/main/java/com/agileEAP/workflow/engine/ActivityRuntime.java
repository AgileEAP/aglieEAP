package com.agileEAP.workflow.engine;

import java.util.List;
import java.util.ArrayList;
import com.agileEAP.workflow.entity.*;
import com.agileEAP.workflow.engine.enums.*;

/** 
  实现推动和更改具体某个Activity状态
 
*/
public class ActivityRuntime
{
	private IWorkflowPersistence workflowPersistence;
	private boolean waiting = false;
	
	public final IWorkflowPersistence getPersistence()
	{
		return workflowPersistence;
	}
	private ActivityContext context;
	public final ActivityContext getContext()
	{
		return context;
	}
	public final void setContext(ActivityContext value)
	{
		context = value;
	}
	public ActivityRuntime(IWorkflowPersistence workflowPersistence, ActivityContext context)
	{
		this.workflowPersistence = workflowPersistence;
		this.context=context;
	}

	public final void Run()
	{
		waiting = true;

		while (waiting)
		{
			waiting = false;
			ActivityExecutingEvent tempVar = new ActivityExecutingEvent();
			tempVar.setUUID(String.format("%1$s-%2$s-%3$s-ActivityExecutingEvent", getContext().getProcessDefine().getID(), getContext().getProcessDefine().getVersion(), getContext().getActivity().getID()));
			tempVar.setContext(getContext());
			EventPublisher.<ActivityExecutingEvent>Publish(tempVar);

			for (WorkItem workItem : GetExecutingWorkItems(getContext().getActivityInst()))
			{
				WorkItemContext workItemContext = new WorkItemContext();
				workItemContext.setProcessDefine(getContext().getProcessDefine());
				workItemContext.setProcessInst(getContext().getProcessInst());
				workItemContext.setActivityInst(getContext().getActivityInst());
				workItemContext.setWorkItem(workItem);
				workItemContext.setParameters(getContext().getParameters());

				WorkItemRuntime workItemRuntime = new WorkItemRuntime(workflowPersistence, workItemContext);

				workItemRuntime.Run(workItem);
			}

			ActivityExecutedEvent activityExecutedEvent = new ActivityExecutedEvent();
			activityExecutedEvent.setUUID(String.format("%1$s-%2$s-%3$s-ActivityExecutedEvent", getContext().getProcessDefine().getID(), getContext().getProcessDefine().getVersion(), getContext().getActivity().getID()));
			activityExecutedEvent.setContext(getContext());
			EventPublisher.<ActivityExecutedEvent>Publish(activityExecutedEvent);
		}
	}

	public final List<WorkItem> GetExecutingWorkItems(ActivityInst activityInst)
	{
		List<WorkItem> executingWorkItems=new ArrayList<WorkItem>();
		for(WorkItem workItem:getPersistence().GetWorkItems(activityInst.getId()))
		{
			if( workItem.getCurrentState()== (short)WorkItemStatus.Executing.getValue())
			{
				executingWorkItems.add(workItem);
			}
		}
		
		return executingWorkItems;
	}

	//private void UpdateActiviyInstStatus(ActivityInst activityInst)
	//{
	//    //TODO: 还需要考虑其他条件和状态(Cancelled? Failed?)来判断Workflow的状态
	//    //TODO: 如果是新启动一个Task，还可能需要判断InComingDependency
	//    //TODO: 可能还要判断Dependency是否强制还是Default的

	//    TaskInstance ti = GetTaskInstance(task.TaskInstanceId);

	//    //TODO:
	//    //ExternalRuleCheckResult result = ExternalRuleCheck.ChekTaskStatusConstraint(taskInstanceId);

	//    if (task.NeedToSetToNewStatus)
	//    {
	//        if (ti.TaskStatus != task.NewStatus)
	//        {
	//            UpdateTaskStatus(ti, task.NewStatus, task);

	//            //If new start task, Check and assign work?
	//        }
	//    }
	//    else
	//    {
	//        switch (ti.TaskStatus)
	//        {
	//            case TaskStatus.not_started: //Start a not started Task
	//                UpdateTaskStatus(ti, TaskStatus.ready_to_start, task);
	//                //TODO: AssignWork
	//                break;
	//            case TaskStatus.ready_to_start:
	//                break;
	//            case TaskStatus.In_process:
	//                break;
	//            case TaskStatus.completed:
	//                break;
	//            case TaskStatus.Failed:
	//                break;
	//        }
	//    }
	//}
	//private void GetToCheckTasks()
	//{
	//    //TODO: 这里只是简单地检测当前在处理的Task，如果策略不同，需要修改

	//    foreach (TaskInstance ti in _taskInstances)
	//    {
	//        TaskDefinition taskDefinition = GetTaskDefinition(ti.TaskInstanceId);

	//        if (TaskInNeedToCheckStatus(ti.TaskStatus))
	//        {
	//            //Need to check if all predessors are completed (or no predessors)
	//            if (AllTaskPredecessorsCompleted(ti))
	//            {
	//                _tasksToCheck.Add(new NeedToCheckTask(ti.TaskInstanceId, false, TaskStatus.not_started));
	//            }
	//        }
	//    }
	//}
	//private bool TaskInNeedToCheckStatus(TaskStatus status)
	//{
	//    return status == TaskStatus.not_started
	//        || status == TaskStatus.ready_to_start
	//        || status == TaskStatus.In_process;
	//}
	//private bool AllTaskPredecessorsCompleted(TaskInstance taskInstance)
	//{
	//    IList<TaskInstance> predecessors = GetTaskPredecessors(taskInstance);
	//    bool result = true;

	//    foreach (TaskInstance ti in predecessors)
	//    {
	//        if (ti.TaskStatus != TaskStatus.completed)
	//        {
	//            result = false;
	//            break;
	//        }
	//    }
	//    return result;
	//}
	//private IList<TaskInstance> GetTaskPredecessors(TaskInstance taskInstance)
	//{
	//    IList<TaskInstance> result = new List<TaskInstance>();

	//    IList<TaskRoute> tds = Persistence.GetTaskInRoutes(taskInstance.TaskInstanceId);
	//    foreach (TaskInstance ti in _taskInstances)
	//    {
	//        foreach (TaskRoute td in tds)
	//        {
	//            if (ti.TaskInstanceId == td.TaskFrontId)
	//                result.Add(ti);
	//        }
	//    }

	//    return result;
	//}

	///// <summary>
	///// 尽量保证这里是更改Task状态的唯一地方
	///// </summary>
	///// <param name="ti"></param>
	///// <param name="newStatus"></param>
	///// <param name="task"></param>
	//private void UpdateTaskStatus(TaskInstance ti, TaskStatus newStatus, NeedToCheckTask task)
	//{
	//    Persistence.UpdateTaskStatus(ti.TaskInstanceId, newStatus);
	//    ti.TaskStatus = newStatus;

	//    //
	//    _needToCheckAgain = true;

	//    //TODO: Check and Send out emails
	//}

	//private TaskDefinition GetTaskDefinition(int taskDefinitionId)
	//{
	//    foreach (TaskDefinition td in workItems)
	//    {
	//        if (td.TaskDefinitionId == taskDefinitionId)
	//            return td;
	//    }
	//    return null; //TODO: throw exceptions
	//}
	//private TaskInstance GetTaskInstance(int taskInstanceId)
	//{
	//    foreach (TaskInstance ti in _taskInstances)
	//    {
	//        if (ti.TaskInstanceId == taskInstanceId)
	//            return ti;
	//    }
	//    return null; //TODO: throw exceptions
	//}
}