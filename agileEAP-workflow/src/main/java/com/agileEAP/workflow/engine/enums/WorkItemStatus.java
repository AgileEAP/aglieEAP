package com.agileEAP.workflow.engine.enums;

/** 
 工作项状态
 
*/
public enum WorkItemStatus
{
	/** 
	 待执行
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("待执行")]
	WaitExecute(1),

	/** 
	 停止
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("停止")]
	Stopped(2),

	/** 
	 执行
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("执行")]
	Executing(3),

	/** 
	 挂起
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("挂起")]
	Suspended(4),

	/** 
	 完成
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("完成")]
	Compeleted(5),

	/** 
	 终止
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("终止")]
	Terminated(6),

	/** 
	 取消
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("取消")]
	Canceled(7),

	/** 
	 出错
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("出错")]
	Error(8);

	private int intValue;
	private static java.util.HashMap<Integer, WorkItemStatus> mappings;
	private synchronized static java.util.HashMap<Integer, WorkItemStatus> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, WorkItemStatus>();
		}
		return mappings;
	}

	private WorkItemStatus(int value)
	{
		intValue = value;
		WorkItemStatus.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static WorkItemStatus forValue(int value)
	{
		return getMappings().get(value);
	}
}