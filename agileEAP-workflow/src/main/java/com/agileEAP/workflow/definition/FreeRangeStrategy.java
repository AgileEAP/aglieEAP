package com.agileEAP.workflow.definition;


/** 
 自由范围设置策略
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("自由范围设置策略")]
public enum FreeRangeStrategy
{
	/** 
	 在该流程范围内自由
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("在该流程范围内自由")]
	FreeWithinProcess(1),

	/** 
	 在指定活动列表范围内自由
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("在指定活动列表范围内自由")]
	FreeWithinActivities(2),

	/** 
	 在后继活动范围内自由
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("在后继活动范围内自由")]
	FreeWithinNextActivites(3);

	private int intValue;
	private static java.util.HashMap<Integer, FreeRangeStrategy> mappings;
	private synchronized static java.util.HashMap<Integer, FreeRangeStrategy> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, FreeRangeStrategy>();
		}
		return mappings;
	}

	private FreeRangeStrategy(int value)
	{
		intValue = value;
		FreeRangeStrategy.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static FreeRangeStrategy forValue(int value)
	{
		return getMappings().get(value);
	}
}