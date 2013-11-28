package com.agileEAP.workflow.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 代理候选人
* @author trh
*/
public  class AgentCandidate {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 代理人
    */
    private String agentToID;
    /**
    * 代理人名称
    */
    private String agentToName;
    /**
    * 代理人类型
    */
    private short agentToType;
    /**
    * 委托人ID
    */
    private String agentFrom;

    public String  getId()
    {
        return  this.id;
    }
        
    public void setId(String  id)
    {
        this.id=id;
    }        
        
    public String  getAgentToID()
    {
        return  this.agentToID;
    }
        
    public void setAgentToID(String  agentToID)
    {
        this.agentToID=agentToID;
    }
    public String  getAgentToName()
    {
        return  this.agentToName;
    }
        
    public void setAgentToName(String  agentToName)
    {
        this.agentToName=agentToName;
    }
    public short  getAgentToType()
    {
        return  this.agentToType;
    }
        
    public void setAgentToType(short  agentToType)
    {
        this.agentToType=agentToType;
    }
    public String  getAgentFrom()
    {
        return  this.agentFrom;
    }
        
    public void setAgentFrom(String  agentFrom)
    {
        this.agentFrom=agentFrom;
    }
}
