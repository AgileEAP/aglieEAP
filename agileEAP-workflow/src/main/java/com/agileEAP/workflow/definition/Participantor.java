package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 参与者信息
 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Participantor 
{
	/** 
	 编号
	 
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
	 参与者名称
	 
	*/
	private String name;
	public final String getName()
	{
		return name;
	}
	public final void setName(String value)
	{
		name=value;
	}

	/** 
	 参与者类别:person,role,org三类
	 
	*/
	private ParticipantorType participantorType=ParticipantorType.Role;
	public final ParticipantorType getParticipantorType()
	{
		return participantorType;
	}
	public final void setParticipantorType(ParticipantorType value)
	{
		participantorType= value;
	}

	/** 
	 父结点ID
	 
	*/

	private String parentID;
	public final String getParentID()
	{
		return parentID;
	}
	public final void setParentID(String value)
	{
		parentID = value;
	}

	/** 
	 序号
	 
	*/
	private int sortOrder;
	public final int getSortOrder()
	{
		return sortOrder;
	}
	public final void setSortOrder(int value)
	{
		sortOrder=value;
	}
}