package com.agileEAP.workflow.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 代理项
* @author trh
*/
public  class AgentItem {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 代理项类型
    */
    private short type;
    /**
    * 代理业务ID
    */
    private String relatedBizID;
    /**
    * 是否生效
    */
    private short isValid;
    /**
    * 代理权限
    */
    private short agentPrivilege;
    /**
    * 所属代理
    */
    private String agentID;

    public String  getId()
    {
        return  this.id;
    }
        
    public void setId(String  id)
    {
        this.id=id;
    }        
        
    public short  getType()
    {
        return  this.type;
    }
        
    public void setType(short  type)
    {
        this.type=type;
    }
    public String  getRelatedBizID()
    {
        return  this.relatedBizID;
    }
        
    public void setRelatedBizID(String  relatedBizID)
    {
        this.relatedBizID=relatedBizID;
    }
    public short  getIsValid()
    {
        return  this.isValid;
    }
        
    public void setIsValid(short  isValid)
    {
        this.isValid=isValid;
    }
    public short  getAgentPrivilege()
    {
        return  this.agentPrivilege;
    }
        
    public void setAgentPrivilege(short  agentPrivilege)
    {
        this.agentPrivilege=agentPrivilege;
    }
    public String  getAgentID()
    {
        return  this.agentID;
    }
        
    public void setAgentID(String  agentID)
    {
        this.agentID=agentID;
    }
}
