package com.agileEAP.workflow.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 工作项
* @author trh
*/
public  class WorkItem {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 名称
    */
    private String name;
    /**
    * 工作项类型
    */
    private short type;
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
    * 创建者姓名
    */
    private String creatorName;
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
    * 描述
    */
    private String description;
    /**
    * 当前状态
    */
    private short currentState;
    /**
    * 参与者
    */
    private String participant;
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
    * 提醒时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date remindTime;
    /**
    * 响应URL
    */
    private String actionURL;
    /**
    * 操作码
    */
    private String actionMask;
    /**
    * 所属流程实例
    */
    private String processInstID;
    /**
    * 所属流程实例名
    */
    private String processInstName;
    /**
    * 所属活动实例
    */
    private String activityInstID;
    /**
    * 所属活动实像名
    */
    private String activityInstName;
    /**
    * 所属流程
    */
    private String processID;
    /**
    * 所属流程显示名
    */
    private String processName;
    /**
    * 是否允许代理
    */
    private short allowAgent;
    /**
    * 业务状态
    */
    private short bizState;
    /**
    * 执行者
    */
    private String executor;
    /**
    * 执行者姓名
    */
    private String executorName;
    /**
    * 执行时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date executeTime;
    /**
    * 所属根流程实例
    */
    private String rootProcessInstID;

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
    public String  getCreatorName()
    {
        return  this.creatorName;
    }
        
    public void setCreatorName(String  creatorName)
    {
        this.creatorName=creatorName;
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
    public String  getDescription()
    {
        return  this.description;
    }
        
    public void setDescription(String  description)
    {
        this.description=description;
    }
    public short  getCurrentState()
    {
        return  this.currentState;
    }
        
    public void setCurrentState(short  currentState)
    {
        this.currentState=currentState;
    }
    public String  getParticipant()
    {
        return  this.participant;
    }
        
    public void setParticipant(String  participant)
    {
        this.participant=participant;
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
    public Date  getRemindTime()
    {
        return  this.remindTime;
    }
        
    public void setRemindTime(Date  remindTime)
    {
        this.remindTime=remindTime;
    }
    public String  getActionURL()
    {
        return  this.actionURL;
    }
        
    public void setActionURL(String  actionURL)
    {
        this.actionURL=actionURL;
    }
    public String  getActionMask()
    {
        return  this.actionMask;
    }
        
    public void setActionMask(String  actionMask)
    {
        this.actionMask=actionMask;
    }
    public String  getProcessInstID()
    {
        return  this.processInstID;
    }
        
    public void setProcessInstID(String  processInstID)
    {
        this.processInstID=processInstID;
    }
    public String  getProcessInstName()
    {
        return  this.processInstName;
    }
        
    public void setProcessInstName(String  processInstName)
    {
        this.processInstName=processInstName;
    }
    public String  getActivityInstID()
    {
        return  this.activityInstID;
    }
        
    public void setActivityInstID(String  activityInstID)
    {
        this.activityInstID=activityInstID;
    }
    public String  getActivityInstName()
    {
        return  this.activityInstName;
    }
        
    public void setActivityInstName(String  activityInstName)
    {
        this.activityInstName=activityInstName;
    }
    public String  getProcessID()
    {
        return  this.processID;
    }
        
    public void setProcessID(String  processID)
    {
        this.processID=processID;
    }
    public String  getProcessName()
    {
        return  this.processName;
    }
        
    public void setProcessName(String  processName)
    {
        this.processName=processName;
    }
    public short  getAllowAgent()
    {
        return  this.allowAgent;
    }
        
    public void setAllowAgent(short  allowAgent)
    {
        this.allowAgent=allowAgent;
    }
    public short  getBizState()
    {
        return  this.bizState;
    }
        
    public void setBizState(short  bizState)
    {
        this.bizState=bizState;
    }
    public String  getExecutor()
    {
        return  this.executor;
    }
        
    public void setExecutor(String  executor)
    {
        this.executor=executor;
    }
    public String  getExecutorName()
    {
        return  this.executorName;
    }
        
    public void setExecutorName(String  executorName)
    {
        this.executorName=executorName;
    }
    public Date  getExecuteTime()
    {
        return  this.executeTime;
    }
        
    public void setExecuteTime(Date  executeTime)
    {
        this.executeTime=executeTime;
    }
    public String  getRootProcessInstID()
    {
        return  this.rootProcessInstID;
    }
        
    public void setRootProcessInstID(String  rootProcessInstID)
    {
        this.rootProcessInstID=rootProcessInstID;
    }
}
