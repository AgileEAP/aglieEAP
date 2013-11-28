package com.agileEAP.workflow.definition;

/** 
 时间限制策略
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("时间限制策略")]
public enum TimeLimitStrategy
{
	/** 
	 自定义时间限制
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("自定义时间限制")]
	LimitTime(1),

	/** 
	 从相关数据获取时间限制
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("从相关数据获取时间限制")]
	RelevantLimitTime(2);

	private int intValue;
	private static java.util.HashMap<Integer, TimeLimitStrategy> mappings;
	private synchronized static java.util.HashMap<Integer, TimeLimitStrategy> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, TimeLimitStrategy>();
		}
		return mappings;
	}

	private TimeLimitStrategy(int value)
	{
		intValue = value;
		TimeLimitStrategy.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static TimeLimitStrategy forValue(int value)
	{
		return getMappings().get(value);
	}
}