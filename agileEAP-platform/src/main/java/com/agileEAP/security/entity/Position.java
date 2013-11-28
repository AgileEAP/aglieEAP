package com.agileEAP.security.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 岗位定义表
* @author trh
*/
public  class Position {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 岗位代码
    */
    private String code;
    /**
    * 岗位名称
    */
    private String name;
    /**
    * 职务ID
    */
    private String dutyID;
    /**
    * 应用ID
    */
    private String appID;
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
    public String  getDutyID()
    {
        return  this.dutyID;
    }
        
    public void setDutyID(String  dutyID)
    {
        this.dutyID=dutyID;
    }
    public String  getAppID()
    {
        return  this.appID;
    }
        
    public void setAppID(String  appID)
    {
        this.appID=appID;
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
