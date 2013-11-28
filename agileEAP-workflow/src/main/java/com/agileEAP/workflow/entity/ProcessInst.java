package com.agileEAP.workflow.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 流程实例
* @author trh
*/
public  class ProcessInst {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 名称
    */
    private String name;
    /**
    * 流程定义ID
    */
    private String processDefID;
    /**
    * 流程名称
    */
    private String processDefName;
    /**
    * 父流程
    */
    private String parentProcessID;
    /**
    * 父活动
    */
    private String parentActivityID;
    /**
    * 当前状态
    */
    private short currentState;
    /**
    * 限期时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date limitTime;
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
    * 终止时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date finalTime;
    /**
    * 提醒时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date remindTime;
    /**
    * 是否超时
    */
    private short isTimeOut;
    /**
    * 超时时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date timeOutTime;
    /**
    * 所属流程版本
    */
    private String processVersion;
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
        
    public String  getName()
    {
        return  this.name;
    }
        
    public void setName(String  name)
    {
        this.name=name;
    }
    public String  getProcessDefID()
    {
        return  this.processDefID;
    }
        
    public void setProcessDefID(String  processDefID)
    {
        this.processDefID=processDefID;
    }
    public String  getProcessDefName()
    {
        return  this.processDefName;
    }
        
    public void setProcessDefName(String  processDefName)
    {
        this.processDefName=processDefName;
    }
    public String  getParentProcessID()
    {
        return  this.parentProcessID;
    }
        
    public void setParentProcessID(String  parentProcessID)
    {
        this.parentProcessID=parentProcessID;
    }
    public String  getParentActivityID()
    {
        return  this.parentActivityID;
    }
        
    public void setParentActivityID(String  parentActivityID)
    {
        this.parentActivityID=parentActivityID;
    }
    public short  getCurrentState()
    {
        return  this.currentState;
    }
        
    public void setCurrentState(short  currentState)
    {
        this.currentState=currentState;
    }
    public Date  getLimitTime()
    {
        return  this.limitTime;
    }
        
    public void setLimitTime(Date  limitTime)
    {
        this.limitTime=limitTime;
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
    public Date  getFinalTime()
    {
        return  this.finalTime;
    }
        
    public void setFinalTime(Date  finalTime)
    {
        this.finalTime=finalTime;
    }
    public Date  getRemindTime()
    {
        return  this.remindTime;
    }
        
    public void setRemindTime(Date  remindTime)
    {
        this.remindTime=remindTime;
    }
    public short  getIsTimeOut()
    {
        return  this.isTimeOut;
    }
        
    public void setIsTimeOut(short  isTimeOut)
    {
        this.isTimeOut=isTimeOut;
    }
    public Date  getTimeOutTime()
    {
        return  this.timeOutTime;
    }
        
    public void setTimeOutTime(Date  timeOutTime)
    {
        this.timeOutTime=timeOutTime;
    }
    public String  getProcessVersion()
    {
        return  this.processVersion;
    }
        
    public void setProcessVersion(String  processVersion)
    {
        this.processVersion=processVersion;
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
