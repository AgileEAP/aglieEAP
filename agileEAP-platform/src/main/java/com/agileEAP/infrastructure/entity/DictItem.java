package com.agileEAP.infrastructure.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 字典项
* @author trh
*/
public  class DictItem {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 所属字典
    */
    private String dictID;
    /**
    * 字典项值
    */
    private String value;
    /**
    * 字典项显示值
    */
    private String text;
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
        
    public String  getDictID()
    {
        return  this.dictID;
    }
        
    public void setDictID(String  dictID)
    {
        this.dictID=dictID;
    }
    public String  getValue()
    {
        return  this.value;
    }
        
    public void setValue(String  value)
    {
        this.value=value;
    }
    public String  getText()
    {
        return  this.text;
    }
        
    public void setText(String  text)
    {
        this.text=text;
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
