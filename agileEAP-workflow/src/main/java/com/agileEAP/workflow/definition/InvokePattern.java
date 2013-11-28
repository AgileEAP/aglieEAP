package com.agileEAP.workflow.definition;

/** 
 调用模式
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("调用模式")]
public enum InvokePattern
{
	/** 
	 同步
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("同步")]
	Synchronous(1),

	/** 
	 异步
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("异步")]
	Asynchronous(2);

	private int intValue;
	private static java.util.HashMap<Integer, InvokePattern> mappings;
	private synchronized static java.util.HashMap<Integer, InvokePattern> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, InvokePattern>();
		}
		return mappings;
	}

	private InvokePattern(int value)
	{
		intValue = value;
		InvokePattern.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static InvokePattern forValue(int value)
	{
		return getMappings().get(value);
	}
}