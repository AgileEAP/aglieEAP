package com.agileEAP.workflow.engine.enums;

/** 
 工作项业务状态
 
*/
public enum WorkItemBizStatus
{
	/** 
	 正常
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("正常")]
	Common(1),

	/** 
	 代理
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("代理")]
	Agent(2),

	/** 
	 代办
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("代办")]
	Delegate(3),

	/** 
	 协办
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("协办")]
	Help(4),

	/** 
	 待确认
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("待确认")]
	WaitConfirm(5),

	/** 
	 修改
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("修改")]
	ReDo(6);

	private int intValue;
	private static java.util.HashMap<Integer, WorkItemBizStatus> mappings;
	private synchronized static java.util.HashMap<Integer, WorkItemBizStatus> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, WorkItemBizStatus>();
		}
		return mappings;
	}

	private WorkItemBizStatus(int value)
	{
		intValue = value;
		WorkItemBizStatus.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static WorkItemBizStatus forValue(int value)
	{
		return getMappings().get(value);
	}
}