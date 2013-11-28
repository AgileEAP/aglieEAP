package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public final class Point3D
{
	private double x;
	public double getX()
	{
		return x;
	}
	public void setX(double value)
	{
		x = value;
	}

	private double y;
	public double getY()
	{
		return y;
	}
	public void setY(double value)
	{
		y = value;
	}

	private int z;
	public int getZ()
	{
		return z;
	}
	public void setZ(int value)
	{
		z = value;
	}
}