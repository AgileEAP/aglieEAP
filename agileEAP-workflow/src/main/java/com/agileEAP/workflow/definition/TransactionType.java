package com.agileEAP.workflow.definition;

/** 
 事物处理方式
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("事物处理方式")]
public enum TransactionType
{
	/** 
	 联合
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("联合")]
	Join(1),

	/** 
	 挂起
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("挂起")]
	Suspend(2);

	private int intValue;
	private static java.util.HashMap<Integer, TransactionType> mappings;
	private synchronized static java.util.HashMap<Integer, TransactionType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, TransactionType>();
		}
		return mappings;
	}

	private TransactionType(int value)
	{
		intValue = value;
		TransactionType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static TransactionType forValue(int value)
	{
		return getMappings().get(value);
	}
}