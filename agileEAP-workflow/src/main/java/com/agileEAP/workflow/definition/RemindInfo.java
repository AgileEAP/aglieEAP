package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/** 
 提醒信息
 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class RemindInfo 
{
	/** 
	 提醒类型
	 
	*/
	private RemindType remindType=RemindType.Email;
	public final RemindType getRemindType()
	{
		return remindType;
	}
	public final void setRemindType(RemindType value)
	{
		remindType= value;
	}

	/** 
	 提醒策略
	 
	*/
	private RemindStrategy remindStrategy=RemindStrategy.RemindLimtTime;
	public final RemindStrategy getRemindStrategy()
	{
		return remindStrategy;
	}
	public final void setRemindStrategy(RemindStrategy value)
	{
		remindStrategy= value;
	}


	/** 
	 天
	 
	*/
	private int remindLimtTimeDay;
	public final int getRemindLimtTimeDay()
	{
		return remindLimtTimeDay;
	}
	public final void setRemindLimtTimeDay(int value)
	{
		remindLimtTimeDay= value;
	}

	/** 
	 小时
	 
	*/
	private int remindLimtTimeHour;
	public final int getRemindLimtTimeHour()
	{
		return remindLimtTimeHour;
	}
	public final void setRemindLimtTimeHour(int value)
	{
		remindLimtTimeHour= value;
	}

	/** 
	 分钟
	 
	*/
	private int remindLimtTimeMinute;
	public final int getRemindLimtTimeMinute()
	{
		return remindLimtTimeMinute;
	}
	public final void setRemindLimtTimeMinute(int value)
	{
		remindLimtTimeMinute= value;
	}

	/** 
	 提醒相关数据
	 
	*/
	private String remindRelevantData;
	public final String getRemindRelevantData()
	{
		return remindRelevantData;
	}
	public final void setRemindRelevantData(String value)
	{
		remindRelevantData= value;
	}

	/** 
	 是否发送提醒信息
	 
	*/
	private boolean isSendMessageForRemind;
	public final boolean getIsSendMessageForRemind()
	{
		return isSendMessageForRemind;
	}
	public final void setIsSendMessageForRemind(boolean value)
	{
		isSendMessageForRemind=value;
	}
}