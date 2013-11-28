package com.agileEAP.workflow.definition;

/** 
 重启活动参与者
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("重启活动参与者")]
public enum ResetParticipant
{
	/** 
	 最初参与者
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("最初参与者")]
	FirstParticipantor(1),

	/** 
	 最终参与者
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("最终参与者")]
	LastParticipantor(2);

	private int intValue;
	private static java.util.HashMap<Integer, ResetParticipant> mappings;
	private synchronized static java.util.HashMap<Integer, ResetParticipant> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, ResetParticipant>();
		}
		return mappings;
	}

	private ResetParticipant(int value)
	{
		intValue = value;
		ResetParticipant.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ResetParticipant forValue(int value)
	{
		return getMappings().get(value);
	}
}