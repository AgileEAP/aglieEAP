package com.agileEAP.workflow.definition;


/** 
 日历类型
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("日历类型")]
public enum CalendarType
{
	/** 
	 默认日历
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("默认日历")]
	CDefault(1),

	/** 
	 24x7日历
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("24x7日历")]
	C24x7(2),

	/** 
	 流程日历
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("流程日历")]
	CProcess(3),

	/** 
	 参与者日历
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("参与者日历")]
	CParticipant(4);

	private int intValue;
	private static java.util.HashMap<Integer, CalendarType> mappings;
	private synchronized static java.util.HashMap<Integer, CalendarType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, CalendarType>();
		}
		return mappings;
	}

	private CalendarType(int value)
	{
		intValue = value;
		CalendarType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static CalendarType forValue(int value)
	{
		return getMappings().get(value);
	}
}