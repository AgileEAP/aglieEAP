package com.agileEAP.workflow.viewModel;

public class TransitionModel
{
	private String id;
	public final String getID()
	{
		return id;
	}
	public final void setID(String value)
	{
		id = value;
	}
	private String DestActInstName;
	public final String getDestActInstName()
	{
		return DestActInstName;
	}
	public final void setDestActInstName(String value)
	{
		DestActInstName = value;
	}
	private String CurrentState;
	public final String getCurrentState()
	{
		return CurrentState;
	}
	public final void setCurrentState(String value)
	{
		CurrentState = value;
	}
	private String Executor;
	public final String getExecutor()
	{
		return Executor;
	}
	public final void setExecutor(String value)
	{
		Executor = value;
	}
	private String ExecutorName;
	public final String getExecutorName()
	{
		return ExecutorName;
	}
	public final void setExecutorName(String value)
	{
		ExecutorName = value;
	}
	private String OrgName;
	public final String getOrgName()
	{
		return OrgName;
	}
	public final void setOrgName(String value)
	{
		OrgName = value;
	}
	 java.util.Date ExecuteTime = new java.util.Date(0);
	public final java.util.Date getExecuteTime()
	{
		return ExecuteTime;
	}
	public final void setExecuteTime(java.util.Date value)
	{
		ExecuteTime = value;
	}
}