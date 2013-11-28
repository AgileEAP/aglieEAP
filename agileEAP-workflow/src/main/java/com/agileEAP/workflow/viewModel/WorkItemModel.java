package com.agileEAP.workflow.viewModel;

public class WorkItemModel
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
	private String Name;
	public final String getName()
	{
		return Name;
	}
	public final void setName(String value)
	{
		Name = value;
	}
	private String ProcessInstID;
	public final String getProcessInstID()
	{
		return ProcessInstID;
	}
	public final void setProcessInstID(String value)
	{
		ProcessInstID = value;
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
	private String Creator;
	public final String getCreator()
	{
		return Creator;
	}
	public final void setCreator(String value)
	{
		Creator = value;
	}
	private String CreatorName;
	public final String getCreatorName()
	{
		return CreatorName;
	}
	public final void setCreatorName(String value)
	{
		CreatorName = value;
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
	 java.util.Date StartTime = new java.util.Date(0);
	public final java.util.Date getStartTime()
	{
		return StartTime;
	}
	public final void setStartTime(java.util.Date value)
	{
		StartTime = value;
	}
}