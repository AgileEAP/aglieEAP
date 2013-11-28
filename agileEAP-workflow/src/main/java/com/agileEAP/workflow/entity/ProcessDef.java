package com.agileEAP.workflow.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 流程定义
* @author trh
*/
public  class ProcessDef {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 名称
    */
    private String name;
    /**
    * 显示名
    */
    private String text;
    /**
    * 流程内容
    */
    private String content;
    /**
    * 所属分类
    */
    private String categoryID;
    /**
    * 当前状态
    */
    private short currentState;
    /**
    * 是否当前版本
    */
    private short currentFlag;
    /**
    * 流程启动者
    */
    private String startor;
    /**
    * 是否有活动实例
    */
    private short isActive;
    /**
    * 版本
    */
    private String version;
    /**
    * 描述
    */
    private String description;
    /**
    * 创建时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
    * 创建者
    */
    private String creator;
    /**
    * 修改时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    /**
    * 修改人
    */
    private String modifier;

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
    public String  getContent()
    {
        return  this.content;
    }
        
    public void setContent(String  content)
    {
        this.content=content;
    }
    public String  getCategoryID()
    {
        return  this.categoryID;
    }
        
    public void setCategoryID(String  categoryID)
    {
        this.categoryID=categoryID;
    }
    public short  getCurrentState()
    {
        return  this.currentState;
    }
        
    public void setCurrentState(short  currentState)
    {
        this.currentState=currentState;
    }
    public short  getCurrentFlag()
    {
        return  this.currentFlag;
    }
        
    public void setCurrentFlag(short  currentFlag)
    {
        this.currentFlag=currentFlag;
    }
    public String  getStartor()
    {
        return  this.startor;
    }
        
    public void setStartor(String  startor)
    {
        this.startor=startor;
    }
    public short  getIsActive()
    {
        return  this.isActive;
    }
        
    public void setIsActive(short  isActive)
    {
        this.isActive=isActive;
    }
    public String  getVersion()
    {
        return  this.version;
    }
        
    public void setVersion(String  version)
    {
        this.version=version;
    }
    public String  getDescription()
    {
        return  this.description;
    }
        
    public void setDescription(String  description)
    {
        this.description=description;
    }
    public Date  getCreateTime()
    {
        return  this.createTime;
    }
        
    public void setCreateTime(Date  createTime)
    {
        this.createTime=createTime;
    }
    public String  getCreator()
    {
        return  this.creator;
    }
        
    public void setCreator(String  creator)
    {
        this.creator=creator;
    }
    public Date  getModifyTime()
    {
        return  this.modifyTime;
    }
        
    public void setModifyTime(Date  modifyTime)
    {
        this.modifyTime=modifyTime;
    }
    public String  getModifier()
    {
        return  this.modifier;
    }
        
    public void setModifier(String  modifier)
    {
        this.modifier=modifier;
    }
}
