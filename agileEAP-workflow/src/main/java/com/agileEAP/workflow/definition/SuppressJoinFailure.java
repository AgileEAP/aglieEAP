package com.agileEAP.workflow.definition;


/** 
 抑制联合事物方式
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("抑制联合事物方式")]
public enum SuppressJoinFailure
{
	/** 
	 抑制
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("抑制")]
	Suppress(1),

	/** 
	 不做处理
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("不做处理")]
	None(2);

	private int intValue;
	private static java.util.HashMap<Integer, SuppressJoinFailure> mappings;
	private synchronized static java.util.HashMap<Integer, SuppressJoinFailure> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, SuppressJoinFailure>();
		}
		return mappings;
	}

	private SuppressJoinFailure(int value)
	{
		intValue = value;
		SuppressJoinFailure.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static SuppressJoinFailure forValue(int value)
	{
		return getMappings().get(value);
	}
}