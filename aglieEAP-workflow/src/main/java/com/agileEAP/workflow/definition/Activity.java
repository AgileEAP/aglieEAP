package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import org.eclipse.persistence.oxm.annotations.XmlCustomizer;

/** 
 工作流活动基类
*/

@JsonTypeInfo(  
	    use = JsonTypeInfo.Id.NAME,  
	    include = JsonTypeInfo.As.PROPERTY,  
	    property = "activityType")  
	@JsonSubTypes({  
	    @Type(value = StartActivity.class, name = "StartActivity"),
	    @Type(value = ManualActivity.class, name = "ManualActivity"),
	    @Type(value = AutoActivity.class, name = "AutoActivity"),
	    @Type(value = SubflowActivity.class, name = "SubflowActivity"),
	    @Type(value = RouterActivity.class, name = "RouterActivity"),
	    @Type(value = EndActivity.class, name = "EndActivity") }) 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlCustomizer(ActivityCustomizer.class)
public class Activity 
{
	/** 
	 活动ID
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
	 活动名称
	 
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

	/** 
	 活动类型
	 
	*/
	private ActivityType activityType= ActivityType.ManualActivity;
	public final ActivityType getActivityType()
	{
		return activityType;
	}
	public final void setActivityType(ActivityType value)
	{
		activityType= value;
	}

	/** 
	 分支模式
	 
	*/
	private SplitType splitType= SplitType.XOR;
	public final SplitType getSplitType()
	{
		return splitType;
	}
	public final void setSplitType(SplitType value)
	{
		splitType=value;
	}
	/** 
	 聚合模式
	 
	*/
	private JoinType joinType= JoinType.AND;
	public final JoinType getJoinType()
	{
		return joinType;
	}
	public final void setJoinType(JoinType value)
	{
		joinType= value;
	}

	/** 
	 是否分享事物
	 
	*/
	private boolean isSplitTransaction= false;
	public final boolean getIsSplitTransaction()
	{
		return isSplitTransaction;
	}
	public final void setIsSplitTransaction(boolean value)
	{
		isSplitTransaction= value;
	}

	/** 
	 优先级别
	 
	*/
	private PriorityType priority=  PriorityType.Middle;
	public final PriorityType getPriority()
	{
		return priority;
	}
	public final void setPriority(PriorityType value)
	{
		priority= value;
	}


	/** 
	 说明
	 
	*/
	private String description;
	public final String getDescription()
	{
		return description;
	}
	public final void setDescription(String value)
	{
		description= value;
	}

	/** 
	 显示样式
	 
	*/
	private Style style;
	public final Style getStyle()
	{
		return style;
	}
	public final void setStyle(Style value)
	{
		style = value;
	}

	/** 
	 超时限制
	 
	*/
	private TimeLimit timeLimit;
	public final TimeLimit getTimeLimit()
	{
		return timeLimit;
	}
	public final void setTimeLimit(TimeLimit value)
	{
		timeLimit = value;
	}

	/** 
	 触发事件
	 
	*/

	private java.util.ArrayList<TriggerEvent> triggerEvents;
	public final java.util.ArrayList<TriggerEvent> getTriggerEvents()
	{
		return triggerEvents;
	}
	public final void setTriggerEvents(java.util.ArrayList<TriggerEvent> value)
	{
		triggerEvents = value;
	}

	/** 
	 活动参数
	 
	*/

	private java.util.ArrayList<Parameter> parameters;
	public final java.util.ArrayList<Parameter> getParameters()
	{
		return parameters;
	}
	public final void setParameters(java.util.ArrayList<Parameter> value)
	{
		parameters = value;
	}
}