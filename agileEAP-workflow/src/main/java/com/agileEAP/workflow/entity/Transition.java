package com.agileEAP.workflow.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 流程迁移记录
* @author trh
*/
public  class Transition {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 源活动定义ID
    */
    private String srcActID;
    /**
    * 源活动ID
    */
    private String srcActInstID;
    /**
    * 源活动名称
    */
    private String srcActInstName;
    /**
    * 源活动定义名称
    */
    private String srcActName;
    /**
    * 目标活动ID
    */
    private String destActInstID;
    /**
    * 目标活动名称
    */
    private String destActInstName;
    /**
    * 目标活动定义ID
    */
    private String destActID;
    /**
    * 目标活动定义名称
    */
    private String destActName;
    /**
    * 流程实例ID
    */
    private String processInstID;
    /**
    * 流程实例名称
    */
    private String processInstName;
    /**
    * 迁移时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date transTime;

    public String  getId()
    {
        return  this.id;
    }
        
    public void setId(String  id)
    {
        this.id=id;
    }        
        
    public String  getSrcActID()
    {
        return  this.srcActID;
    }
        
    public void setSrcActID(String  srcActID)
    {
        this.srcActID=srcActID;
    }
    public String  getSrcActInstID()
    {
        return  this.srcActInstID;
    }
        
    public void setSrcActInstID(String  srcActInstID)
    {
        this.srcActInstID=srcActInstID;
    }
    public String  getSrcActInstName()
    {
        return  this.srcActInstName;
    }
        
    public void setSrcActInstName(String  srcActInstName)
    {
        this.srcActInstName=srcActInstName;
    }
    public String  getSrcActName()
    {
        return  this.srcActName;
    }
        
    public void setSrcActName(String  srcActName)
    {
        this.srcActName=srcActName;
    }
    public String  getDestActInstID()
    {
        return  this.destActInstID;
    }
        
    public void setDestActInstID(String  destActInstID)
    {
        this.destActInstID=destActInstID;
    }
    public String  getDestActInstName()
    {
        return  this.destActInstName;
    }
        
    public void setDestActInstName(String  destActInstName)
    {
        this.destActInstName=destActInstName;
    }
    public String  getDestActID()
    {
        return  this.destActID;
    }
        
    public void setDestActID(String  destActID)
    {
        this.destActID=destActID;
    }
    public String  getDestActName()
    {
        return  this.destActName;
    }
        
    public void setDestActName(String  destActName)
    {
        this.destActName=destActName;
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
    public Date  getTransTime()
    {
        return  this.transTime;
    }
        
    public void setTransTime(Date  transTime)
    {
        this.transTime=transTime;
    }
}
