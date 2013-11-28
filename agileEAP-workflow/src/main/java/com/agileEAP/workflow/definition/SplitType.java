package com.agileEAP.workflow.definition;

/** 
 活动分支模式
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("分支模式")]
public enum SplitType
{
	/** 
	 单一分支
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("单一分支")]
	XOR(1),

	/** 
	 多路分支
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("多路分支")]
	OR(2),

	/** 
	 全部分支
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("全部分支")]
	AND(3);

	private int intValue;
	private static java.util.HashMap<Integer, SplitType> mappings;
	private synchronized static java.util.HashMap<Integer, SplitType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, SplitType>();
		}
		return mappings;
	}

	private SplitType(int value)
	{
		intValue = value;
		SplitType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static SplitType forValue(int value)
	{
		return getMappings().get(value);
	}
}