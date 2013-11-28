package com.agileEAP.workflow.definition;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 活动迁移定义类
 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Transition 
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties 成员
	/** 
	 连接ID
	 
	*/
	private String id=UUID.randomUUID().toString();
	public final String getID()
	{
		return id;
	}
	public final void setID(String value)
	{
		id=value;
	}



	/** 
	连接显示名称
	 
	*/
	private String name="";
	public final String getName()
	{
		return name;
	}
	public final void setName(String value)
	{
		name=value;
	}

	/** 
	显示皮肤
	 
	*/
	private String skin="default";
	public final String getSkin()
	{
		return skin;
	}
	public final void setSkin(String value)
	{
		skin=value;
	}

	/** 
	 是否是默认连接
	 
	*/
	private boolean isDefault;
	public final boolean getIsDefault()
	{
		return isDefault;
	}
	public final void setIsDefault(boolean value)
	{
		isDefault=value;
	}

	/** 
	 Z轴
	 
	*/
	private int zIndex;
	public final int getZIndex()
	{
		return zIndex;
	}
	public final void setZIndex(int value)
	{
		zIndex=value;
	}

	/** 
	 连接开始结点位置
	 
	*/
	private String sourceOrientation;
	public final String getSourceOrientation()
	{
		return sourceOrientation;
	}
	public final void setSourceOrientation(String value)
	{
		sourceOrientation = value;
	}

	/** 
	 源点位置
	 
	*/
	private Point3D sourcePoint;
	public final Point3D getSourcePoint()
	{
		return sourcePoint;
	}
	public final void setSourcePoint(Point3D value)
	{
		sourcePoint = value;
	}

	/** 
	 连接结束结点位置
	 
	*/
	private String sinkOrientation;
	public final String getSinkOrientation()
	{
		return sinkOrientation;
	}
	public final void setSinkOrientation(String value)
	{
		sinkOrientation = value;
	}

	/** 
	 目标点位置
	 
	*/
	private Point3D sinkPoint;
	public final Point3D getSinkPoint()
	{
		return sinkPoint;
	}
	public final void setSinkPoint(Point3D value)
	{
		sinkPoint = value;
	}

	/** 
	 连接开始结点
	 
	*/
	private String srcActivity;
	public final String getSrcActivity()
	{
		return srcActivity;
	}
	public final void setSrcActivity(String value)
	{
		srcActivity = value;
	}

	/** 
	 连接结束结点ID
	 
	*/
	private String destActivity;
	public final String getDestActivity()
	{
		return destActivity;
	}
	public final void setDestActivity(String value)
	{
		destActivity = value;
	}

	/** 
	 表达式值
	 
	*/
	private String expression;
	public final String getExpression()
	{
		return expression;
	}
	public final void setExpression(String value)
	{
		expression=value;
	}

	/** 
	 优先级
	 
	*/
	private PriorityType priority=PriorityType.Middle;
	public final PriorityType getPriority()
	{
		return priority;
	}
	public final void setPriority(PriorityType value)
	{
		priority=value;
	}

	/** 
	 描述
	 
	*/
	private String description;
	public final String getDescription()
	{
		return description;
	}
	public final void setDescription(String value)
	{
		description=value;
	}

	/** 
	 连接折点
	 
	*/
	private java.util.ArrayList<BendPoint> bendPoints;
	public final java.util.ArrayList<BendPoint> getBendPoints()
	{
		return bendPoints;
	}
	public final void setBendPoints(java.util.ArrayList<BendPoint> value)
	{
		bendPoints = value;
	}
}