package com.agileEAP.workflow.definition;


/** 
 响应方式
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("响应方式")]
public enum ActionPattern
{
	/** 
	 方法
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("方法")]
	Method(1),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("Web服务")]
	WebService(2),

	/** 
	 业务操作
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("业务操作")]
	BusinessOperation(3);

	private int intValue;
	private static java.util.HashMap<Integer, ActionPattern> mappings;
	private synchronized static java.util.HashMap<Integer, ActionPattern> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, ActionPattern>();
		}
		return mappings;
	}

	private ActionPattern(int value)
	{
		intValue = value;
		ActionPattern.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ActionPattern forValue(int value)
	{
		return getMappings().get(value);
	}
}