package com.agileEAP.workflow.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 流程参与者
* @author trh
*/
public  class Participant {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 名称
    */
    private String name;
    /**
    * 参与者类型
    */
    private short participantType;
    /**
    * 参与者值ID
    */
    private String participantID;
    /**
    * 工作项ID
    */
    private String workItemID;
    /**
    * 工作项状态
    */
    private short workItemState;
    /**
    * 参与类型
    */
    private short partiInType;
    /**
    * 代办类型
    */
    private short delegateType;
    /**
    * 参与顺序
    */
    private int participantIndex;
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
    public short  getParticipantType()
    {
        return  this.participantType;
    }
        
    public void setParticipantType(short  participantType)
    {
        this.participantType=participantType;
    }
    public String  getParticipantID()
    {
        return  this.participantID;
    }
        
    public void setParticipantID(String  participantID)
    {
        this.participantID=participantID;
    }
    public String  getWorkItemID()
    {
        return  this.workItemID;
    }
        
    public void setWorkItemID(String  workItemID)
    {
        this.workItemID=workItemID;
    }
    public short  getWorkItemState()
    {
        return  this.workItemState;
    }
        
    public void setWorkItemState(short  workItemState)
    {
        this.workItemState=workItemState;
    }
    public short  getPartiInType()
    {
        return  this.partiInType;
    }
        
    public void setPartiInType(short  partiInType)
    {
        this.partiInType=partiInType;
    }
    public short  getDelegateType()
    {
        return  this.delegateType;
    }
        
    public void setDelegateType(short  delegateType)
    {
        this.delegateType=delegateType;
    }
    public int  getParticipantIndex()
    {
        return  this.participantIndex;
    }
        
    public void setParticipantIndex(int  participantIndex)
    {
        this.participantIndex=participantIndex;
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
