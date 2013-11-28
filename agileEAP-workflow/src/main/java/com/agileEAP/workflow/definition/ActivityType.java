package com.agileEAP.workflow.definition;


/** 
 活动类型
 
*/
public enum ActivityType
{
	/** 
	 开始活动
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("开始活动")]
	StartActivity(1),

	/** 
	 人工活动
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("人工活动")]
	ManualActivity(2),

	/** 
	 路由活动
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("路由活动")]
	RouterActivity(3),

	/** 
	 子流程活动
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("子流程活动")]
	SubflowActivity(4),
	/** 
	 自动活动
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("自动活动")]
	AutoActivity(5),

	/** 
	 结束活动
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("结束活动")]
	EndActivity(6),

	/** 
	 处理活动
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("处理活动")]
	ProcessActivity(7);

	private int intValue;
	private static java.util.HashMap<Integer, ActivityType> mappings;
	private synchronized static java.util.HashMap<Integer, ActivityType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, ActivityType>();
		}
		return mappings;
	}

	private ActivityType(int value)
	{
		intValue = value;
		ActivityType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ActivityType forValue(int value)
	{
		return getMappings().get(value);
	}
}