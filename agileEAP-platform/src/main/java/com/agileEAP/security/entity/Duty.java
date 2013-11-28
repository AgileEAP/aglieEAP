package com.agileEAP.security.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 定义职务及上下级关系
* @author trh
*/
public  class Duty {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 职务编码
    */
    private String code;
    /**
    * 职务名称
    */
    private String name;
    /**
    * 上级职务
    */
    private String parentID;
    /**
    * 例如科技类，审计类等
    */
    private short type;
    /**
    * 描述
    */
    private String description;
    /**
    * 创建者
    */
    private String creator;
    /**
    * 创建时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public String  getId()
    {
        return  this.id;
    }
        
    public void setId(String  id)
    {
        this.id=id;
    }        
        
    public String  getCode()
    {
        return  this.code;
    }
        
    public void setCode(String  code)
    {
        this.code=code;
    }
    public String  getName()
    {
        return  this.name;
    }
        
    public void setName(String  name)
    {
        this.name=name;
    }
    public String  getParentID()
    {
        return  this.parentID;
    }
        
    public void setParentID(String  parentID)
    {
        this.parentID=parentID;
    }
    public short  getType()
    {
        return  this.type;
    }
        
    public void setType(short  type)
    {
        this.type=type;
    }
    public String  getDescription()
    {
        return  this.description;
    }
        
    public void setDescription(String  description)
    {
        this.description=description;
    }
    public String  getCreator()
    {
        return  this.creator;
    }
        
    public void setCreator(String  creator)
    {
        this.creator=creator;
    }
    public Date  getCreateTime()
    {
        return  this.createTime;
    }
        
    public void setCreateTime(Date  createTime)
    {
        this.createTime=createTime;
    }
}
