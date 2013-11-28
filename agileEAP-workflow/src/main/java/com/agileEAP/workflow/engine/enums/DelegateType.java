package com.agileEAP.workflow.engine.enums;

/** 
 代办类型
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("代办类型")]
public enum DelegateType
{
	/** 
	 主办
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("主办")]
	Sponsor(0),

	/** 
	 代办
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("代办")]
	Delegate(1),

	/** 
	 协办
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("协办")]
	Assistant(2);

	private int intValue;
	private static java.util.HashMap<Integer, DelegateType> mappings;
	private synchronized static java.util.HashMap<Integer, DelegateType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, DelegateType>();
		}
		return mappings;
	}

	private DelegateType(int value)
	{
		intValue = value;
		DelegateType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static DelegateType forValue(int value)
	{
		return getMappings().get(value);
	}
}