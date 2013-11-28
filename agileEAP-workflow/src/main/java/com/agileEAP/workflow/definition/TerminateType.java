package com.agileEAP.workflow.definition;

/** 
 终结方式
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("终结方式")]
public enum TerminateType
{
	/** 
	 自动
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("自动")]
	Auto(1),

	/** 
	 人工
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("人工")]
	Manual(2);

	private int intValue;
	private static java.util.HashMap<Integer, TerminateType> mappings;
	private synchronized static java.util.HashMap<Integer, TerminateType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, TerminateType>();
		}
		return mappings;
	}

	private TerminateType(int value)
	{
		intValue = value;
		TerminateType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static TerminateType forValue(int value)
	{
		return getMappings().get(value);
	}
}