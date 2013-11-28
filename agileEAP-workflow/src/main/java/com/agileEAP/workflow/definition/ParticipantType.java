package com.agileEAP.workflow.definition;

/** 
 参与类型
 
*/
public enum ParticipantType
{
	/** 
	///// 参与者
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("参与者")]
	Participantor(1),

	/** 
	 流程启动者
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("流程启动者")]
	ProcessStarter(2),

	/** 
	 特殊活动
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("特殊活动")]
	SpecialActivity(3),

	/** 
	 相关数据
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("相关数据")]
	RelevantData(4),

	/** 
	 自定义规则
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("自定义规则")]
	CustomRegular(5),

	/** 
	 流程执行者
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("流程执行者")]
	ProcessExecutor(6),

	/** 
	 相关规则
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("相关规则")]
	RelateRegular(7);

	private int intValue;
	private static java.util.HashMap<Integer, ParticipantType> mappings;
	private synchronized static java.util.HashMap<Integer, ParticipantType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, ParticipantType>();
		}
		return mappings;
	}

	private ParticipantType(int value)
	{
		intValue = value;
		ParticipantType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ParticipantType forValue(int value)
	{
		return getMappings().get(value);
	}
}