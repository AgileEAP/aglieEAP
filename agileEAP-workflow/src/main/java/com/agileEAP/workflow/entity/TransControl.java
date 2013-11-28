package com.agileEAP.workflow.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 流程迁移控制
* @author trh
*/
public  class TransControl {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 源活动定义ID
    */
    private String srcActID;
    /**
    * 源活动定义名称
    */
    private String srcActName;
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
    * 迁移时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date transTime;
    /**
    * 迁移权重
    */
    private float transWeight;

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
    public String  getSrcActName()
    {
        return  this.srcActName;
    }
        
    public void setSrcActName(String  srcActName)
    {
        this.srcActName=srcActName;
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
    public Date  getTransTime()
    {
        return  this.transTime;
    }
        
    public void setTransTime(Date  transTime)
    {
        this.transTime=transTime;
    }
    public float  getTransWeight()
    {
        return  this.transWeight;
    }
        
    public void setTransWeight(float  transWeight)
    {
        this.transWeight=transWeight;
    }
}
