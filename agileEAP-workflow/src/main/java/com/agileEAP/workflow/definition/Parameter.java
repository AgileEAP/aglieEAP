package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 参数
 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Parameter 
{
	/** 
	 参数名称
	 
	*/
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
	 参数方向
	 
	*/
	private ParameterDirection direction= ParameterDirection.Ref;
	public final ParameterDirection getDirection()
	{
		return direction;
	}
	public final void setDirection(ParameterDirection value)
	{
		direction= value;
	}


	/** 
	 参数值
	 
	*/
	private String value;
	public final String getValue()
	{
		return this.value;
	}
	public final void setValue(String value)
	{
		this.value= value;
	}
}