package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/** 
 折点
 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class BendPoint 
{
	/** 
	 X轴位置
	 
	*/
	private double x;
	public final double getX()
	{
		return x;
	}
	public final void setX(double value)
	{
		x= value;
	}

	/** 
	 Y轴位置
	 
	*/
	private double y;
	public final double getY()
	{
		return y;
	}
	public final void setY(double value)
	{
		y=value;
	}


	/** 
	 Z轴位置
	 
	*/
	private double z;
	public final double getZ()
	{
		return z;
	}
	public final void setZ(double value)
	{
		z= value;
	}

	/** 
	 源点
	 
	*/
	private Point3D source;
	public final Point3D getSource()
	{
		return source;
	}
	public final void setSource(Point3D value)
	{
		source=value;
	}

	/** 
	 目标点
	 
	*/
	private Point3D sink;
	public final Point3D getSink()
	{
		return sink;
	}
	public final void setSink(Point3D value)
	{
		sink= value;
	}

	/** 
	 是否已经有中折点
	 
	*/
	private boolean isBend;
	public final boolean getIsBend()
	{
		return isBend;
	}
	public final void setIsBend(boolean value)
	{
		isBend=value;
	}
}