package com.agileEAP.workflow.engine.enums;

/** 
 工作流状态
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("工作流状态")]
public enum ProcessStatus
{
	/** 
	 候选版本，还未发布
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("未发布")]
	Candidate(0),

	/** 
	 已经发布
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("已发布")]
	Release(1),

	/** 
	 终止
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("终止")]
	Terminated(2);

	private int intValue;
	private static java.util.HashMap<Integer, ProcessStatus> mappings;
	private synchronized static java.util.HashMap<Integer, ProcessStatus> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, ProcessStatus>();
		}
		return mappings;
	}

	private ProcessStatus(int value)
	{
		intValue = value;
		ProcessStatus.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ProcessStatus forValue(int value)
	{
		return getMappings().get(value);
	}
}