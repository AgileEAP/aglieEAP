package com.agileEAP.security.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 元数据
* @author trh
*/
public  class MetaData {
    /**
    * ID
    */
    private String id;
        
    /**
    * 元数据名称
    */
    private String name;
    /**
    * 元数据类型
    */
    private short type;
    /**
    * 值
    */
    private String value;

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
    public short  getType()
    {
        return  this.type;
    }
        
    public void setType(short  type)
    {
        this.type=type;
    }
    public String  getValue()
    {
        return  this.value;
    }
        
    public void setValue(String  value)
    {
        this.value=value;
    }
}
