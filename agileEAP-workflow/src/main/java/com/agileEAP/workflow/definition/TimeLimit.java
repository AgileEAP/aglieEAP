package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 超时设置
 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class TimeLimit 
{
	/** 
	 是否启用超时设置
	 
	*/
	private boolean isTimeLimitSet;
	public final boolean getIsTimeLimitSet()
	{
		return isTimeLimitSet;
	}
	public final void setIsTimeLimitSet(boolean value)
	{
		isTimeLimitSet= value;
	}

	/** 
	 工作日历
	 
	*/
	private CalendarSet calendarSet;
	public final CalendarSet getCalendarSet()
	{
		return calendarSet;
	}
	public final void setCalendarSet(CalendarSet value)
	{
		calendarSet = value;
	}

	/** 
	 超时信息
	 
	*/
	private TimeLimitInfo timeLimitInfo;
	public final TimeLimitInfo getTimeLimitInfo()
	{
		return timeLimitInfo;
	}
	public final void setTimeLimitInfo(TimeLimitInfo value)
	{
		timeLimitInfo = value;
	}

	/** 
	 提醒信息
	 
	*/
	private RemindInfo remindInfo;
	public final RemindInfo getRemindInfo()
	{
		return remindInfo;
	}
	public final void setRemindInfo(RemindInfo value)
	{
		remindInfo = value;
	}

}