package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;

/** 
 响应URL
 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ActionURL 
{
	/** 
	 是否自定义URL
	 
	*/
	private boolean isSpecifyURL=false;
	public  boolean getIsSpecifyURL()
	{
		return isSpecifyURL;
	}
	public  void setIsSpecifyURL(boolean value)
	{
		isSpecifyURL= value;
	}

	/** 
	 URL类型,LogicalProcess(逻辑处理),WebURL(web展现),Other(其他)
	 
	*/
	
	private URLType urlType=URLType.CustomURL;
	
	@JsonProperty("urlType")
	public final URLType getURLType()
	{
		return urlType;
	}
	public final void setURLType(URLType value)
	{
		urlType=value;
	}

	/** 
	 调用URL
	 
	*/
	private String specifyURL;
	public final String getSpecifyURL()
	{
		return specifyURL;
	}
	public final void setSpecifyURL(String value)
	{
		specifyURL= value;
	}

	/** 
	 人工处理
	 
	*/
	private CustomAction privateManualProcess;
	public final CustomAction getManualProcess()
	{
		return privateManualProcess;
	}
	public final void setManualProcess(CustomAction value)
	{
		privateManualProcess = value;
	}
}