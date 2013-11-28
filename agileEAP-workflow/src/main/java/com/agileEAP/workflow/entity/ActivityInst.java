package com.agileEAP.workflow.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 活动实例
* @author trh
*/
public  class ActivityInst {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 名称
    */
    private String name;
    /**
    * 活动类型
    */
    private short type;
    /**
    * 当前状态
    */
    private short currentState;
    /**
    * 启动时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
    * 结束时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
    * 子流程实例ID
    */
    private String subProcessInstID;
    /**
    * 活动定义ID
    */
    private String activityDefID;
    /**
    * 所属流程实例
    */
    private String processInstID;
    /**
    * 回退标志
    */
    private short rollbackFlag;
    /**
    * 描述
    */
    private String description;
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
    public short  getCurrentState()
    {
        return  this.currentState;
    }
        
    public void setCurrentState(short  currentState)
    {
        this.currentState=currentState;
    }
    public Date  getStartTime()
    {
        return  this.startTime;
    }
        
    public void setStartTime(Date  startTime)
    {
        this.startTime=startTime;
    }
    public Date  getEndTime()
    {
        return  this.endTime;
    }
        
    public void setEndTime(Date  endTime)
    {
        this.endTime=endTime;
    }
    public String  getSubProcessInstID()
    {
        return  this.subProcessInstID;
    }
        
    public void setSubProcessInstID(String  subProcessInstID)
    {
        this.subProcessInstID=subProcessInstID;
    }
    public String  getActivityDefID()
    {
        return  this.activityDefID;
    }
        
    public void setActivityDefID(String  activityDefID)
    {
        this.activityDefID=activityDefID;
    }
    public String  getProcessInstID()
    {
        return  this.processInstID;
    }
        
    public void setProcessInstID(String  processInstID)
    {
        this.processInstID=processInstID;
    }
    public short  getRollbackFlag()
    {
        return  this.rollbackFlag;
    }
        
    public void setRollbackFlag(short  rollbackFlag)
    {
        this.rollbackFlag=rollbackFlag;
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
}
