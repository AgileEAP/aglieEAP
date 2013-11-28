package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/** 
 工作日历
 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CalendarSet 
{
	/** 
	 初始类型
	*/
	private String init;
	public final String getInit()
	{
		return init;
	}
	public final void setInit(String value)
	{
		init= value;
	}

	/** 
	 日历类型
	 
	*/
	private CalendarType type= CalendarType.CDefault;
	public final CalendarType getType()
	{
		return type;
	}
	public final void setType(CalendarType value)
	{
		type=value;
	}


	/** 
	 日历标记
	 
	*/
	private String id;
	public final String getID()
	{
		return id;
	}
	public final void setID(String value)
	{
		id= value;
	}

	/** 
	 日历名称
	 
	*/
	private String name;
	public final String getName()
	{
		return name;
	}
	public final void setName(String value)
	{
		name= value;
	}
}