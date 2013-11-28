package com.agileEAP.security.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 用户自定义的快捷菜单
* @author trh
*/
public  class Shortcut {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 操作员ID
    */
    private String operatorID;
    /**
    * 权限ID
    */
    private String privilegeID;
    /**
    * 序号
    */
    private int sortOrder;
    /**
    * 快捷菜单图标
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
    public String  getPrivilegeID()
    {
        return  this.privilegeID;
    }
        
    public void setPrivilegeID(String  privilegeID)
    {
        this.privilegeID=privilegeID;
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
