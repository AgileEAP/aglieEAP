package com.agileEAP.infrastructure.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 数据字典
* @author trh
*/
public  class Dict {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 字典名
    */
    private String name;
    /**
    * 字典显示名
    */
    private String text;
    /**
    * 父字典
    */
    private String parentID;
    /**
    * 序号
    */
    private int sortOrder;
    /**
    * 描述
    */
    private String description;

    public String  getId()
    {
        return  this.id;
    }
        
    public void setId(String  id)
    {
        this.id=id;
    }        
        
    public String  getName()
    {
        return  this.name;
    }
        
    public void setName(String  name)
    {
        this.name=name;
    }
    public String  getText()
    {
        return  this.text;
    }
        
    public void setText(String  text)
    {
        this.text=text;
    }
    public String  getParentID()
    {
        return  this.parentID;
    }
        
    public void setParentID(String  parentID)
    {
        this.parentID=parentID;
    }
    public int  getSortOrder()
    {
        return  this.sortOrder;
    }
        
    public void setSortOrder(int  sortOrder)
    {
        this.sortOrder=sortOrder;
    }
    public String  getDescription()
    {
        return  this.description;
    }
        
    public void setDescription(String  description)
    {
        this.description=description;
    }
}
