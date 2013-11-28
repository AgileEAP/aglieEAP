package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 自由流规则
 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class FreeFlowRule 
{
	/** 
	 该活动是否为自由活动
	 
	*/
	private boolean isFreeActivity;
	public final boolean getIsFreeActivity()
	{
		return isFreeActivity;
	}
	public final void setIsFreeActivity(boolean value)
	{
		isFreeActivity=value;
	}

	/** 
	 自由范围设置策略
	 
	*/
	private FreeRangeStrategy freeRangeStrategy=FreeRangeStrategy.FreeWithinNextActivites;
	public final FreeRangeStrategy getFreeRangeStrategy()
	{
		return freeRangeStrategy;
	}
	public final void setFreeRangeStrategy(FreeRangeStrategy value)
	{
		freeRangeStrategy= value;
	}

	/** 
	 指定活动表范围内自由
	 
	*/

	private java.util.ArrayList<Activity> freeRangeActivities;
	public final java.util.ArrayList<Activity> getFreeRangeActivities()
	{
		return freeRangeActivities;
	}
	public final void setFreeRangeActivities(java.util.ArrayList<Activity> value)
	{
		freeRangeActivities = value;
	}

	/** 
	 流向的目标活动仅限于人式活动
	 
	*/
	private boolean isOnlyLimitedManualActivity;
	public final boolean getIsOnlyLimitedManualActivity()
	{
		return isOnlyLimitedManualActivity;
	}
	public final void setIsOnlyLimitedManualActivity(boolean value)
	{
		isOnlyLimitedManualActivity= value;
	}
}