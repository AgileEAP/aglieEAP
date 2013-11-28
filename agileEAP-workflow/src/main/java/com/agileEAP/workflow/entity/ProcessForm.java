package com.agileEAP.workflow.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 工作流表单
* @author trh
*/
public  class ProcessForm {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 工作项ID
    */
    private String workItemID;
    /**
    * 业务表
    */
    private String bizTable;
    /**
    * 业务ID
    */
    private String bizID;
    /**
    * 查询关键字
    */
    private String keyWord;
    /**
    * 创建时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
    * 创建者
    */
    private String creator;

    public String  getId()
    {
        return  this.id;
    }
        
    public void setId(String  id)
    {
        this.id=id;
    }        
        
    public String  getWorkItemID()
    {
        return  this.workItemID;
    }
        
    public void setWorkItemID(String  workItemID)
    {
        this.workItemID=workItemID;
    }
    public String  getBizTable()
    {
        return  this.bizTable;
    }
        
    public void setBizTable(String  bizTable)
    {
        this.bizTable=bizTable;
    }
    public String  getBizID()
    {
        return  this.bizID;
    }
        
    public void setBizID(String  bizID)
    {
        this.bizID=bizID;
    }
    public String  getKeyWord()
    {
        return  this.keyWord;
    }
        
    public void setKeyWord(String  keyWord)
    {
        this.keyWord=keyWord;
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
}
