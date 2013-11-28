package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 超时信息
 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class TimeLimitInfo 
{
	/** 
	 超时提醒策略
	 
	*/
	private TimeLimitStrategy timeLimitStrategy=TimeLimitStrategy.LimitTime;
	public final TimeLimitStrategy getTimeLimitStrategy()
	{
		return timeLimitStrategy;
	}
	public final void setTimeLimitStrategy(TimeLimitStrategy value)
	{
		timeLimitStrategy=value;
	}


	/** 
	 天
	 
	*/
	private int limitTimeDay;
	public final int getLimitTimeDay()
	{
		return limitTimeDay;
	}
	public final void setLimitTimeDay(int value)
	{
		limitTimeDay= value;
	}

	/** 
	 小时
	 
	*/
	private int limitTimeHour;
	public final int getLimitTimeHour()
	{
		return limitTimeHour;
	}
	public final void setLimitTimeHour(int value)
	{
		limitTimeHour= value;
	}

	/** 
	 分钟
	 
	*/
	private int limitTimeMinute;
	public final int getLimitTimeMinute()
	{
		return limitTimeMinute;
	}
	public final void setLimitTimeMinute(int value)
	{
		limitTimeMinute= value;
	}

	/** 
	 相关数据
	 
	*/
	private String relevantData;
	public final String getRelevantData()
	{
		return relevantData;
	}
	public final void setRelevantData(String value)
	{
		relevantData= value;
	}

	/** 
	 是否发送超时信息
	 
	*/
	private boolean isSendMessageForOvertime;
	public final boolean getIsSendMessageForOvertime()
	{
		return isSendMessageForOvertime;
	}
	public final void setIsSendMessageForOvertime(boolean value)
	{
		isSendMessageForOvertime= value;
	}
}