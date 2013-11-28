package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 活动样式
 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Style 
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	/** 
	 活动皮肤
	 
	*/
	private String skin;
	public final String getSkin()
	{
		return skin;
	}
	public final void setSkin(String value)
	{
		skin= value;
	}

	/** 
	 高度
	 
	*/
	private double height;
	public final double getHeight()
	{
		return height;
	}
	public final void setHeight(double value)
	{
		height= value;
	}


	/** 
	 宽度
	 
	*/
	private double width;
	public final double getWidth()
	{
		return width;
	}
	public final void setWidth(double value)
	{
		width=value;
	}

	/** 
	 左边位置
	 
	*/
	private double left;
	public final double getLeft()
	{
		return left;
	}
	public final void setLeft(double value)
	{
		left= value;
	}

	/** 
	 顶端位置
	 
	*/
	private double top;
	public final double getTop()
	{
		return top;
	}
	public final void setTop(double value)
	{
		top= value;
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
		zIndex= value;
	}

	/** 
	 是否组合
	 
	*/
	private boolean isGroup;
	public final boolean getIsGroup()
	{
		return isGroup;
	}
	public final void setIsGroup(boolean value)
	{
		isGroup= value;
	}
}