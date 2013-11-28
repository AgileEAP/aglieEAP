package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 多工作项
 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class MultiWorkItem 
{
	/** 
	 是否启动多工作项设置
	 
	*/
	private boolean isMulWIValid;
	public final boolean getIsMulWIValid()
	{
		return isMulWIValid;
	}
	public final void setIsMulWIValid(boolean value)
	{
		isMulWIValid= value;
	}

	/** 
	 多工作项分配策略
	 
	*/
	private WorkItemNumStrategy workItemNumStrategy;
	public final WorkItemNumStrategy getWorkItemNumStrategy()
	{
		return workItemNumStrategy;
	}
	public final void setWorkItemNumStrategy(WorkItemNumStrategy value)
	{
		workItemNumStrategy=value;
	}

	/** 
	 完成规则设定
	 
	*/
	private FinishRule finishRule=FinishRule.FinishAll;
	public final FinishRule getFinishRule()
	{
		return finishRule;
	}
	public final void setFinishRule(FinishRule value)
	{
		finishRule= value;
	}

	/** 
	 完成个数
	 
	*/
	private int finishRquiredNum;
	public final int getFinishRquiredNum()
	{
		return finishRquiredNum;
	}
	public final void setFinishRquiredNum(int value)
	{
		finishRquiredNum= value;
	}

	/** 
	 完成百分比
	 
	*/
	private double finishRequiredPercent;
	public final double getFinishRequiredPercent()
	{
		return finishRequiredPercent;
	}
	public final void setFinishRequiredPercent(double value)
	{
		finishRequiredPercent= value;
	}

	/** 
	 未完成工作项是否自动终止
	 
	*/
	private boolean isAutoCancel;
	public final boolean getIsAutoCancel()
	{
		return isAutoCancel;
	}
	public final void setIsAutoCancel(boolean value)
	{
		isAutoCancel=value;
	}

	/** 
	 是否顺序执行
	 
	*/
	private boolean isSequentialExecute;
	public final boolean getIsSequentialExecute()
	{
		return isSequentialExecute;
	}
	public final void setIsSequentialExecute(boolean value)
	{
		isSequentialExecute= value;
	}
}