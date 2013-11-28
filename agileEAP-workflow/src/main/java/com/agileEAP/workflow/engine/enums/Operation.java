package com.agileEAP.workflow.engine.enums;

/** 
 流程操作
 
*/
public enum Operation
{
	/** 
	 认领
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("认领")]
	Claim(1),

	/** 
	 启动
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("启动")]
	Start(2),

	/** 
	 停止
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("停止")]
	Stop(3),

	/** 
	 发布
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("发布")]
	Release(4),

	/** 
	 挂起
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("挂起")]
	Suspend(5),

	/** 
	 恢复
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("恢复")]
	Resume(6),

	/** 
	 跳转
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("跳转")]
	Skip(7),

	/** 
	 委托
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("委托")]
	Delegate(8),

	/** 
	 前进（提交）
	  [Remark("结束活动")]
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("提交")]
	Forward(9),

	/** 
	 完成
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("完成")]
	Complete(10),

	/** 
	 出错
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("出错")]
	Error(11);

	private int intValue;
	private static java.util.HashMap<Integer, Operation> mappings;
	private synchronized static java.util.HashMap<Integer, Operation> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, Operation>();
		}
		return mappings;
	}

	private Operation(int value)
	{
		intValue = value;
		Operation.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static Operation forValue(int value)
	{
		return getMappings().get(value);
	}
}