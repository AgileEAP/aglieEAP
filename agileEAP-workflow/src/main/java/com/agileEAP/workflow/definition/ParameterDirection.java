package com.agileEAP.workflow.definition;

/** 
 参数方向
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("参数方向")]
public enum ParameterDirection
{
	/** 
	 输入参数
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("输入参数")]
	In(1),

	/** 
	 输出参数
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("输出参数")]
	Out(2),

	/** 
	 输入输出参数
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("输入输出参数")]
	Ref(3);

	private int intValue;
	private static java.util.HashMap<Integer, ParameterDirection> mappings;
	private synchronized static java.util.HashMap<Integer, ParameterDirection> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, ParameterDirection>();
		}
		return mappings;
	}

	private ParameterDirection(int value)
	{
		intValue = value;
		ParameterDirection.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ParameterDirection forValue(int value)
	{
		return getMappings().get(value);
	}
}