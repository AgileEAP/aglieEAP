package com.agileEAP.workflow.definition;

/** 
 URL类型,ManualProcess(人工处理),WebURL(web展现)
 
*/
public enum URLType
{
	/** 
	 默认URL
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("默认URL")]
	DefaultURL(1),

	/** 
	 人工处理
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("人工处理")]
	ManualProcess(2),

	/** 
	 Web页面
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("Web页面")]
	CustomURL(3);

	private int intValue;
	private static java.util.HashMap<Integer, URLType> mappings;
	private synchronized static java.util.HashMap<Integer, URLType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, URLType>();
		}
		return mappings;
	}

	private URLType(int value)
	{
		intValue = value;
		URLType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static URLType forValue(int value)
	{
		return getMappings().get(value);
	}
}