package com.agileEAP.workflow.engine.enums;

/** 
 流程实例状态
 
*/
public enum ProcessInstStatus
{
	/** 
	 未启动
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("未启动")]
	NoStart(1),

	/** 
	 运行
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("运行")]
	Running(2),

	/** 
	 挂起
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("挂起")]
	Suspended(3),

	/** 
	 完成
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("完成")]
	Completed(4),

	/** 
	 终止
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("终止")]
	Terminated(5),

	/** 
	 取消
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("取消")]
	Canceled(6);

	private int intValue;
	private static java.util.HashMap<Integer, ProcessInstStatus> mappings;
	private synchronized static java.util.HashMap<Integer, ProcessInstStatus> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, ProcessInstStatus>();
		}
		return mappings;
	}

	private ProcessInstStatus(int value)
	{
		intValue = value;
		ProcessInstStatus.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ProcessInstStatus forValue(int value)
	{
		return getMappings().get(value);
	}
}