package com.agileEAP.workflow.definition;

/** 
 参与者类型
 
*/
public enum ParticipantorType
{
	/** 
	 人员
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("人员")]
	Person(1),

	/** 
	 角色
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("角色")]
	Role(2),

	/** 
	 组织
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("组织")]
	Org(3);

	private int intValue;
	private static java.util.HashMap<Integer, ParticipantorType> mappings;
	private synchronized static java.util.HashMap<Integer, ParticipantorType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, ParticipantorType>();
		}
		return mappings;
	}

	private ParticipantorType(int value)
	{
		intValue = value;
		ParticipantorType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ParticipantorType forValue(int value)
	{
		return getMappings().get(value);
	}
}