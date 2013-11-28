package com.agileEAP.workflow.engine.enums;

/** 
 参与类型-领取（GET）、执行(EXE)、曾经领取(OGET)、曾经执行(OEXE)、执行完成(PEXE)
 
*/
public enum PartiInType
{
	/** 
	 领取
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("领取")]
	Get(1),

	/** 
	 执行
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("执行")]
	Exe(2),

	/** 
	 曾经领取
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("曾经领取")]
	OGet(3),

	/** 
	 曾经执行
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("曾经执行")]
	OExe(4),

	/** 
	 执行完成
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("执行完成")]
	PExe(5);

	private int intValue;
	private static java.util.HashMap<Integer, PartiInType> mappings;
	private synchronized static java.util.HashMap<Integer, PartiInType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, PartiInType>();
		}
		return mappings;
	}

	private PartiInType(int value)
	{
		intValue = value;
		PartiInType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static PartiInType forValue(int value)
	{
		return getMappings().get(value);
	}
}