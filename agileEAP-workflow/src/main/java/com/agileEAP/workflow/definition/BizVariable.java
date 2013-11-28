package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/** 
 业务变量
 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class BizVariable 
{

	private String name;
	public final String getName()
	{
		return name;
	}
	public final void setName(String value)
	{
		name= value;
	}

	/** 
	 变量取值路径
	 
	*/
	private String xpath;
	public final String getXPath()
	{
		return xpath;
	}
	public final void setXPath(String value)
	{
		xpath=value;
	}

	/** 
	 ID
	 
	*/
	private String id;
	public final String getID()
	{
		return id;
	}
	public final void setID(String value)
	{
		id= value;
	}

	/** 
	 变量说明
	 
	*/
	private String description;
	public final String getDescription()
	{
		return description;
	}
	public final void setDescription(String value)
	{
		description= value;
	}
}