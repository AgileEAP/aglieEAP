package com.agileEAP.workflow.definition;

/** 
 活动聚合模式
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("聚合模式")]
public enum JoinType
{
	/** 
	 单一聚合
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("单一聚合")]
	XOR(1),

	/** 
	 多路聚合
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("多路聚合")]
	OR(2),

	/** 
	 全部聚合
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("全部聚合")]
	AND(3);

	private int intValue;
	private static java.util.HashMap<Integer, JoinType> mappings;
	private synchronized static java.util.HashMap<Integer, JoinType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, JoinType>();
		}
		return mappings;
	}

	private JoinType(int value)
	{
		intValue = value;
		JoinType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static JoinType forValue(int value)
	{
		return getMappings().get(value);
	}
}