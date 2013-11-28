package com.agileEAP.workflow.definition;


/** 
 异常处理策略
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("异常处理策略")]
public enum ExceptionStrategy
{
	/** 
	 
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("回滚")]
	Rollback(1),

	/** 
	 
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("忽略")]
	Ignore(2);

	private int intValue;
	private static java.util.HashMap<Integer, ExceptionStrategy> mappings;
	private synchronized static java.util.HashMap<Integer, ExceptionStrategy> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, ExceptionStrategy>();
		}
		return mappings;
	}

	private ExceptionStrategy(int value)
	{
		intValue = value;
		ExceptionStrategy.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ExceptionStrategy forValue(int value)
	{
		return getMappings().get(value);
	}
}