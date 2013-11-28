package com.agileEAP.workflow.viewModel;

import java.util.Date;

public class ProcessDefModel
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
	private String name;
	public final String getName()
	{
		return name;
	}
	public final void setName(String value)
	{
		name = value;
	}
	private String text;
	public final String getText()
	{
		return text;
	}
	public final void setText(String value)
	{
		text = value;
	}
	private String categoryID;
	public final String getCategoryID()
	{
		return categoryID;
	}
	public final void setCategoryID(String value)
	{
		categoryID = value;
	}
	private String currentState;
	public final String getCurrentState()
	{
		return currentState;
	}
	public final void setCurrentState(String value)
	{
		currentState = value;
	}
	private String currentFlag;
	public final String getCurrentFlag()
	{
		return currentFlag;
	}
	public final void setCurrentFlag(String value)
	{
		currentFlag = value;
	}
	private String isActive;
	public final String getIsActive()
	{
		return isActive;
	}
	public final void setIsActive(String value)
	{
		isActive = value;
	}
	private String version;
	public final String getVersion()
	{
		return version;
	}
	public final void setVersion(String value)
	{
		version = value;
	}
	
	private Date createTime;
	public final Date getCreateTime()
	{
		return createTime;
	}
	public final void setCreateTime(Date value)
	{
		createTime = value;
	}
	
}