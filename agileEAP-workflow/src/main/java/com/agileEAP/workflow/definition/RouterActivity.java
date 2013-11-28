package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

//[Remark("路由活动")]
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="activity")
public class RouterActivity extends Activity
{
	/** 
	 是否允许代理
	 
	*/
	private boolean allowAgent;
	public final boolean getAllowAgent()
	{
		return allowAgent;
	}
	public final void setAllowAgent(boolean value)
	{
		allowAgent= value;
	}

	/** 
	 扩展属性，自定义URL
	 
	*/

	private ActionURL customURL;
	public final ActionURL getCustomURL()
	{
		return customURL;
	}
	public final void setCustomURL(ActionURL value)
	{
		customURL = value;
	}
}