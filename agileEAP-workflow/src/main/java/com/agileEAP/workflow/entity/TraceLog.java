package com.agileEAP.workflow.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 流程跟踪日志
* @author trh
*/
public  class TraceLog {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 操作
    */
    private short actionType;
    /**
    * 操作人
    */
    private String operator;
    /**
    * IP地址
    */
    private String clientIP;
    /**
    * 流程ID
    */
    private String processID;
    /**
    * 流程实例ID
    */
    private String processInstID;
    /**
    * 活动ID
    */
    private String activityID;
    /**
    * 活动实例ID
    */
    private String activityInstID;
    /**
    * 工作项ID
    */
    private String workItemID;
    /**
    * 消息
    */
    private String message;
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
        
    public short  getActionType()
    {
        return  this.actionType;
    }
        
    public void setActionType(short  actionType)
    {
        this.actionType=actionType;
    }
    public String  getOperator()
    {
        return  this.operator;
    }
        
    public void setOperator(String  operator)
    {
        this.operator=operator;
    }
    public String  getClientIP()
    {
        return  this.clientIP;
    }
        
    public void setClientIP(String  clientIP)
    {
        this.clientIP=clientIP;
    }
    public String  getProcessID()
    {
        return  this.processID;
    }
        
    public void setProcessID(String  processID)
    {
        this.processID=processID;
    }
    public String  getProcessInstID()
    {
        return  this.processInstID;
    }
        
    public void setProcessInstID(String  processInstID)
    {
        this.processInstID=processInstID;
    }
    public String  getActivityID()
    {
        return  this.activityID;
    }
        
    public void setActivityID(String  activityID)
    {
        this.activityID=activityID;
    }
    public String  getActivityInstID()
    {
        return  this.activityInstID;
    }
        
    public void setActivityInstID(String  activityInstID)
    {
        this.activityInstID=activityInstID;
    }
    public String  getWorkItemID()
    {
        return  this.workItemID;
    }
        
    public void setWorkItemID(String  workItemID)
    {
        this.workItemID=workItemID;
    }
    public String  getMessage()
    {
        return  this.message;
    }
        
    public void setMessage(String  message)
    {
        this.message=message;
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
