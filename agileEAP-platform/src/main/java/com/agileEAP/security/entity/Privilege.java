package com.agileEAP.security.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 权限
* @author trh
*/
public  class Privilege {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 权限名称
    */
    private String name;
    /**
    * 权限类型
    */
    private short type;
    /**
    * 资源ID
    */
    private String resourceID;
    /**
    * 操作ID
    */
    private String operateID;
    /**
    * 元数据ID
    */
    private String metaDataID;
    /**
    * 序号
    */
    private int sortOrder;

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
    public String  getResourceID()
    {
        return  this.resourceID;
    }
        
    public void setResourceID(String  resourceID)
    {
        this.resourceID=resourceID;
    }
    public String  getOperateID()
    {
        return  this.operateID;
    }
        
    public void setOperateID(String  operateID)
    {
        this.operateID=operateID;
    }
    public String  getMetaDataID()
    {
        return  this.metaDataID;
    }
        
    public void setMetaDataID(String  metaDataID)
    {
        this.metaDataID=metaDataID;
    }
    public int  getSortOrder()
    {
        return  this.sortOrder;
    }
        
    public void setSortOrder(int  sortOrder)
    {
        this.sortOrder=sortOrder;
    }
}
