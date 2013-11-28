package com.agileEAP.security.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 用户重组菜单
* @author trh
*/
public  class CustMenu {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 操作员ID
    */
    private String operatorID;
    /**
    * 菜单编号
    */
    private String resourceID;
    /**
    * 菜单名称
    */
    private String name;
    /**
    * 根菜单
    */
    private String rootID;
    /**
    * 父菜单
    */
    private String parentID;
    /**
    * 序号
    */
    private int sortOrder;
    /**
    * 菜单图标
    */
    private String icon;

    public String  getId()
    {
        return  this.id;
    }
        
    public void setId(String  id)
    {
        this.id=id;
    }        
        
    public String  getOperatorID()
    {
        return  this.operatorID;
    }
        
    public void setOperatorID(String  operatorID)
    {
        this.operatorID=operatorID;
    }
    public String  getResourceID()
    {
        return  this.resourceID;
    }
        
    public void setResourceID(String  resourceID)
    {
        this.resourceID=resourceID;
    }
    public String  getName()
    {
        return  this.name;
    }
        
    public void setName(String  name)
    {
        this.name=name;
    }
    public String  getRootID()
    {
        return  this.rootID;
    }
        
    public void setRootID(String  rootID)
    {
        this.rootID=rootID;
    }
    public String  getParentID()
    {
        return  this.parentID;
    }
        
    public void setParentID(String  parentID)
    {
        this.parentID=parentID;
    }
    public int  getSortOrder()
    {
        return  this.sortOrder;
    }
        
    public void setSortOrder(int  sortOrder)
    {
        this.sortOrder=sortOrder;
    }
    public String  getIcon()
    {
        return  this.icon;
    }
        
    public void setIcon(String  icon)
    {
        this.icon=icon;
    }
}
