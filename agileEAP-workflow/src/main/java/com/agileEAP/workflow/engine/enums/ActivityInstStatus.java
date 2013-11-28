package com.agileEAP.workflow.engine.enums;

/** 
 活动实例状态
 
*/
public enum ActivityInstStatus
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
	Compeleted(4),

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
	Canceled(6),

	/** 
	 待激活
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("待激活")]
	WaitActivate(7),

	/** 
	 异常
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("异常")]
	Error(8);

	private int intValue;
	private static java.util.HashMap<Integer, ActivityInstStatus> mappings;
	private synchronized static java.util.HashMap<Integer, ActivityInstStatus> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, ActivityInstStatus>();
		}
		return mappings;
	}

	private ActivityInstStatus(int value)
	{
		intValue = value;
		ActivityInstStatus.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ActivityInstStatus forValue(int value)
	{
		return getMappings().get(value);
	}
}