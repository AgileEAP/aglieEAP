package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

//[Remark("结束活动")]
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="activity")
public class EndActivity extends Activity
{
	/** 
	 激活规则
	 
	*/
	private ActivateRule activateRule;
	public final ActivateRule getActivateRule()
	{
		return activateRule;
	}
	public final void setActivateRule(ActivateRule value)
	{
		activateRule = value;
	}
}