package com.agileEAP.workflow.definition;


/** 
 激活规则
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("激活规则")]
public enum ActivateRuleType
{
	/** 
	 直接运行
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("直接运行")]
	DirectRunning(1),

	/** 
	 待激活
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("待激活")]
	WaitActivate(2),

	/** 
	 自动跳转
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("自动跳转")]
	AutoSkip(3);

	private int intValue;
	private static java.util.HashMap<Integer, ActivateRuleType> mappings;
	private synchronized static java.util.HashMap<Integer, ActivateRuleType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, ActivateRuleType>();
		}
		return mappings;
	}

	private ActivateRuleType(int value)
	{
		intValue = value;
		ActivateRuleType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ActivateRuleType forValue(int value)
	{
		return getMappings().get(value);
	}
}