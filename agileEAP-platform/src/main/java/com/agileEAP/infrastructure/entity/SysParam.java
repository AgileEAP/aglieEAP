package com.agileEAP.infrastructure.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 系统参数
* @author trh
*/
public  class SysParam {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 参数名
    */
    private String name;
    /**
    * 参数值
    */
    private String value;
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
    public String  getValue()
    {
        return  this.value;
    }
        
    public void setValue(String  value)
    {
        this.value=value;
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
