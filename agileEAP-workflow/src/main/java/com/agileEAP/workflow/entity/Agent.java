package com.agileEAP.workflow.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 工作代理
* @author trh
*/
public  class Agent {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 委托人
    */
    private String agentFrom;
    /**
    * 代理人
    */
    private String agentTo;
    /**
    * 代理人类型
    */
    private short agentToType;
    /**
    * 代理方式
    */
    private short agentType;
    /**
    * 生效时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
    * 结束时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
    * 代理原因
    */
    private String agentReason;
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
        
    public String  getAgentFrom()
    {
        return  this.agentFrom;
    }
        
    public void setAgentFrom(String  agentFrom)
    {
        this.agentFrom=agentFrom;
    }
    public String  getAgentTo()
    {
        return  this.agentTo;
    }
        
    public void setAgentTo(String  agentTo)
    {
        this.agentTo=agentTo;
    }
    public short  getAgentToType()
    {
        return  this.agentToType;
    }
        
    public void setAgentToType(short  agentToType)
    {
        this.agentToType=agentToType;
    }
    public short  getAgentType()
    {
        return  this.agentType;
    }
        
    public void setAgentType(short  agentType)
    {
        this.agentType=agentType;
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
    public String  getAgentReason()
    {
        return  this.agentReason;
    }
        
    public void setAgentReason(String  agentReason)
    {
        this.agentReason=agentReason;
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
