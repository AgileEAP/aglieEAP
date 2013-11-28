package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class TriggerEvent 
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties 成员
	/** 
	 ID
	 
	*/
	private String id;
	public final String getID()
	{
		return id;
	}
	public final void setID(String value)
	{
		id=value;
	}

	/** 
	 触发时机,创建（create）,启动（start）,结束（terminate）,超时（overtime）,提醒（remind）
	 
	*/
	private TriggerEventType triggerEventType=TriggerEventType.WorkItemCompleted;
	public final TriggerEventType getTriggerEventType()
	{
		return triggerEventType;
	}
	public final void setTriggerEventType(TriggerEventType value)
	{
		triggerEventType=value;
	}

	/** 
	 调用方式，synchronous同步，asynchronous异步
	 
	*/
	private InvokePattern invokePattern=InvokePattern.Synchronous;
	public final InvokePattern getInvokePattern()
	{
		return invokePattern;
	}
	public final void setInvokePattern(InvokePattern value)
	{
		invokePattern= value;
	}

	/** 
	 事件动作
	 
	*/
	private String eventAction;
	public final String getEventAction()
	{
		return eventAction;
	}
	public final void setEventAction(String value)
	{
		eventAction= value;
	}
}