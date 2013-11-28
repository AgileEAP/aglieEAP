package com.agileEAP.workflow.definition;

/** 
 提醒类型
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("提醒类型")]
public enum RemindType
{
	/** 
	 Email
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("Email")]
	Email(1),

	/** 
	 Email
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("短信")]
	Sms(2),

	/** 
	 电话
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("电话")]
	Phone(3),

	/** 
	 自定义
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("自定义")]
	Custom(4);

	private int intValue;
	private static java.util.HashMap<Integer, RemindType> mappings;
	private synchronized static java.util.HashMap<Integer, RemindType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, RemindType>();
		}
		return mappings;
	}

	private RemindType(int value)
	{
		intValue = value;
		RemindType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static RemindType forValue(int value)
	{
		return getMappings().get(value);
	}
}