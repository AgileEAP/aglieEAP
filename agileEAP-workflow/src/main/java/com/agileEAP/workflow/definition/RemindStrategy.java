package com.agileEAP.workflow.definition;

/** 
 提醒策略
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("提醒策略")]
public enum RemindStrategy
{
	/** 
	 自定义时间限制
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("自定义时间限制")]
	RemindLimtTime(1),

	/** 
	 从相关数据获取时间限制
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("从相关数据获取时间限制")]
	RemindRelevantLimitTime(2);

	private int intValue;
	private static java.util.HashMap<Integer, RemindStrategy> mappings;
	private synchronized static java.util.HashMap<Integer, RemindStrategy> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, RemindStrategy>();
		}
		return mappings;
	}

	private RemindStrategy(int value)
	{
		intValue = value;
		RemindStrategy.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static RemindStrategy forValue(int value)
	{
		return getMappings().get(value);
	}
}