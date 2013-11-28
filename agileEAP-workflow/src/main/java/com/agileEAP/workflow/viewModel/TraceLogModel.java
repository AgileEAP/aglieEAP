package com.agileEAP.workflow.viewModel;

public class TraceLogModel
{
	private String Operator;
	public final String getOperator()
	{
		return Operator;
	}
	public final void setOperator(String value)
	{
		Operator = value;
	}
	private String ClientIP;
	public final String getClientIP()
	{
		return ClientIP;
	}
	public final void setClientIP(String value)
	{
		ClientIP = value;
	}
	private String Message;
	public final String getMessage()
	{
		return Message;
	}
	public final void setMessage(String value)
	{
		Message = value;
	}
	private String ProcessInstName;
	public final String getProcessInstName()
	{
		return ProcessInstName;
	}
	public final void setProcessInstName(String value)
	{
		ProcessInstName = value;
	}
	private String ActivityInstName;
	public final String getActivityInstName()
	{
		return ActivityInstName;
	}
	public final void setActivityInstName(String value)
	{
		ActivityInstName = value;
	}
	private String WorkItemName;
	public final String getWorkItemName()
	{
		return WorkItemName;
	}
	public final void setWorkItemName(String value)
	{
		WorkItemName = value;
	}
	 java.util.Date CreateTime = new java.util.Date(0);
	public final java.util.Date getCreateTime()
	{
		return CreateTime;
	}
	public final void setCreateTime(java.util.Date value)
	{
		CreateTime = value;
	}
}