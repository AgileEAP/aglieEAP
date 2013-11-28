package com.agileEAP.workflow.definition;

///// <summary>
///// 读写方式
///// </summary>
//public enum AccessPattern
//{
//    /// <summary>
//    /// 只读
//    /// </summary>
//    [Remark("只读")]
//    ReadOnly = 1,

//    /// <summary>
//    /// 读写
//    /// </summary>
//    [Remark("读写")]
//    Write = 2
//}

/** 
  触发时机,创建（create）,启动（start）,结束（terminate）,超时（overtime）,提醒（remind）
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("触发事件")]
public enum TriggerEventType
{
	/** 
	 活动创建前
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("活动创建前")]
	ActivityBeforeCreate(1),

	/** 
	 活动启动前
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("活动启动前")]
	ActivityBeforeStart(2),

	/** 
	 活动启动后
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("活动启动后")]
	ActivityAfterStart(3),

	/** 
	 活动超时后
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("活动超时后")]
	ActivityAfterOverTime(4),

	/** 
	 活动终止后
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("活动终止后")]
	ActivityAfterTerminate(5),

	/** 
	 活动完成后
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("活动完成后")]
	ActivityCompleted(6),

	/** 
	 活动提醒前
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("活动提醒前")]
	ActivityBeforeRemind(7),

	/** 
	 工作项创建前
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("工作项创建前")]
	WorkItemBeforeCreate(21),

	/** 
	 工作项创建后
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("工作项创建后")]
	WorkItemAtferCreate(22),

	/** 
	 工作项执行前
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("工作项执行时")]
	WorkItemExecuting(23),

	/** 
	 工作项完成后
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("工作项完成后")]
	WorkItemCompleted(24),

	/** 
	 工作项执行出错时
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("工作项执行出错时")]
	WorkItemError(25),

	/** 
	 工作项取消
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("工作项取消")]
	WorkItemCanncel(26),

	/** 
	 工作项超时
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("工作项超时")]
	WorkItemOverTime(27),

	/** 
	 工作项挂起
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("工作项挂起")]
	WorkItemSuspended(28);

	private int intValue;
	private static java.util.HashMap<Integer, TriggerEventType> mappings;
	private synchronized static java.util.HashMap<Integer, TriggerEventType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, TriggerEventType>();
		}
		return mappings;
	}

	private TriggerEventType(int value)
	{
		intValue = value;
		TriggerEventType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static TriggerEventType forValue(int value)
	{
		return getMappings().get(value);
	}
}