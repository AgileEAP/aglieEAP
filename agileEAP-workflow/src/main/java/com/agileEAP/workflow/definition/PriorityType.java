package com.agileEAP.workflow.definition;

/** 
 优先级类型
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("优先级类型")]
public enum PriorityType
{
	/** 
	 高
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("高")]
	High(1),

	/** 
	 次高
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("次高")]
	SecondaryHigh(2),

	/** 
	 中
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("中")]
	Middle(3),

	/** 
	 次中
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("次中")]
	SecondaryMiddle(4),

	/** 
	 低
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("低")]
	Low(5),

	/** 
	 次低
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("次低")]
	SecondaryLow(6);

	private int intValue;
	private static java.util.HashMap<Integer, PriorityType> mappings;
	private synchronized static java.util.HashMap<Integer, PriorityType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, PriorityType>();
		}
		return mappings;
	}

	private PriorityType(int value)
	{
		intValue = value;
		PriorityType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static PriorityType forValue(int value)
	{
		return getMappings().get(value);
	}
}