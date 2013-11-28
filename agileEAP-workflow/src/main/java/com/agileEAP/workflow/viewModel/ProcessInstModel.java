package com.agileEAP.workflow.viewModel;

public class ProcessInstModel
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
	 java.util.Date CreateTime = new java.util.Date(0);
	public final java.util.Date getCreateTime()
	{
		return CreateTime;
	}
	public final void setCreateTime(java.util.Date value)
	{
		CreateTime = value;
	}
	private String Creator;
	public final String getCreator()
	{
		return Creator;
	}
	public final void setCreator(String value)
	{
		Creator = value;
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
	private String Description;
	public final String getDescription()
	{
		return Description;
	}
	public final void setDescription(String value)
	{
		Description = value;
	}
	 java.util.Date EndTime = new java.util.Date(0);
	public final java.util.Date getEndTime()
	{
		return EndTime;
	}
	public final void setEndTime(java.util.Date value)
	{
		EndTime = value;
	}
	 java.util.Date FinalTime = new java.util.Date(0);
	public final java.util.Date getFinalTime()
	{
		return FinalTime;
	}
	public final void setFinalTime(java.util.Date value)
	{
		FinalTime = value;
	}
	 short IsTimeOut;
	public final short getIsTimeOut()
	{
		return IsTimeOut;
	}
	public final void setIsTimeOut(short value)
	{
		IsTimeOut = value;
	}
	 java.util.Date LimitTime = new java.util.Date(0);
	public final java.util.Date getLimitTime()
	{
		return LimitTime;
	}
	public final void setLimitTime(java.util.Date value)
	{
		LimitTime = value;
	}
	private String Name;
	public final String getName()
	{
		return Name;
	}
	public final void setName(String value)
	{
		Name = value;
	}
	private String ParentActivityID;
	public final String getParentActivityID()
	{
		return ParentActivityID;
	}
	public final void setParentActivityID(String value)
	{
		ParentActivityID = value;
	}
	private String ParentProcessID;
	public final String getParentProcessID()
	{
		return ParentProcessID;
	}
	public final void setParentProcessID(String value)
	{
		ParentProcessID = value;
	}
	private String ProcessDefID;
	public final String getProcessDefID()
	{
		return ProcessDefID;
	}
	public final void setProcessDefID(String value)
	{
		ProcessDefID = value;
	}
	private String ProcessDefName;
	public final String getProcessDefName()
	{
		return ProcessDefName;
	}
	public final void setProcessDefName(String value)
	{
		ProcessDefName = value;
	}
	private String ProcessVersion;
	public final String getProcessVersion()
	{
		return ProcessVersion;
	}
	public final void setProcessVersion(String value)
	{
		ProcessVersion = value;
	}
	 java.util.Date RemindTime = new java.util.Date(0);
	public final java.util.Date getRemindTime()
	{
		return RemindTime;
	}
	public final void setRemindTime(java.util.Date value)
	{
		RemindTime = value;
	}
	 java.util.Date StartTime = new java.util.Date(0);
	public final java.util.Date getStartTime()
	{
		return StartTime;
	}
	public final void setStartTime(java.util.Date value)
	{
		StartTime = value;
	}
	 java.util.Date TimeOutTime = new java.util.Date(0);
	public final java.util.Date getTimeOutTime()
	{
		return TimeOutTime;
	}
	public final void setTimeOutTime(java.util.Date value)
	{
		TimeOutTime = value;
	}
}