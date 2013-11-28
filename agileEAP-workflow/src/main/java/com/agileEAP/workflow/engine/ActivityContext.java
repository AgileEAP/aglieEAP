package com.agileEAP.workflow.engine;

import com.agileEAP.workflow.entity.ProcessInst;
import com.agileEAP.workflow.entity.ActivityInst;
import com.agileEAP.workflow.definition.*;

/** 
 活动上下文
 
*/
public class ActivityContext
{
	/** 
	 流程实例
	 
	*/
	private ProcessInst processInst;
	public final ProcessInst getProcessInst()
	{
		return processInst;
	}
	public final void setProcessInst(ProcessInst value)
	{
		processInst = value;
	}

	/** 
	 活动实例
	 
	*/
	private ActivityInst activityInst;
	public final ActivityInst getActivityInst()
	{
		return activityInst;
	}
	public final void setActivityInst(ActivityInst value)
	{
		activityInst = value;
	}

	private ProcessDefine processDefine;
	public final ProcessDefine getProcessDefine()
	{
		return processDefine;
	}
	public final void setProcessDefine(ProcessDefine value)
	{
		processDefine = value;
	}

	public final Activity getActivity()
	{
		for(Activity activity :getProcessDefine().getActivities())
		{
			if(getActivityInst().getActivityDefID().equals(activity.getID()))
			{
				return activity;
			}
		}
		
		return null;
	}

	/** 
	 参数
	 
	*/
	private java.util.Map<String, Object> parameters;
	public final java.util.Map<String, Object> getParameters()
	{
		return parameters;
	}
	public final void setParameters(java.util.Map<String, Object> value)
	{
		parameters = value;
	}

	/** 
	 构造函数
	 
	 @param engineContext 流程引擎上下文
	 @param processInst 流程实例
	 @param activityInst 活动实例
	*/
	public ActivityContext(ProcessInst processInst, ActivityInst activityInst)
	{
		this.setProcessInst(processInst);
		this.setActivityInst(activityInst);
	}

	/** 
	 构造函数
	 
	*/
	public ActivityContext()
	{
	}
}