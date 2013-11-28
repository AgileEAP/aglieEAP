package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 完成规则
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Remark("完成规则")]
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public enum FinishRule
{
	/** 
	 全部完成
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("全部完成")]
	FinishAll(1),

	/** 
	 完成个数
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("完成个数")]
	SpecifyNum(2),

	/** 
	 完成百分比
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Remark("完成百分比")]
	SpecifyPercent(3);

	private int intValue;
	private static java.util.HashMap<Integer, FinishRule> mappings;
	private synchronized static java.util.HashMap<Integer, FinishRule> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, FinishRule>();
		}
		return mappings;
	}

	private FinishRule(int value)
	{
		intValue = value;
		FinishRule.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static FinishRule forValue(int value)
	{
		return getMappings().get(value);
	}
}