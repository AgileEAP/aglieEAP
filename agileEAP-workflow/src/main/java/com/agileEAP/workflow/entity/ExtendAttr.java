package com.agileEAP.workflow.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 扩展属性
* @author trh
*/
public  class ExtendAttr {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 扩展实体
    */
    private String entity;
    /**
    * 实例ID
    */
    private String entityID;
    /**
    * 属性名
    */
    private String name;
    /**
    * 属性值
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
        
    public String  getEntity()
    {
        return  this.entity;
    }
        
    public void setEntity(String  entity)
    {
        this.entity=entity;
    }
    public String  getEntityID()
    {
        return  this.entityID;
    }
        
    public void setEntityID(String  entityID)
    {
        this.entityID=entityID;
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
}
