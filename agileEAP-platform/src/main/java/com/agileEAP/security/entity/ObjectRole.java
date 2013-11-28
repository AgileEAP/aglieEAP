package com.agileEAP.security.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 设置机构、工作组、岗位、职务等组织对象与角色之间的对应关系
* @author trh
*/
public  class ObjectRole {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 角色ID
    */
    private String roleID;
    /**
    * 取值范围业务字典 ABF_PARTYTYPE
   机构、工作组、岗位、职务
    */
    private short objectType;
    /**
    * 对象ID
    */
    private String objectID;

    public String  getId()
    {
        return  this.id;
    }
        
    public void setId(String  id)
    {
        this.id=id;
    }        
        
    public String  getRoleID()
    {
        return  this.roleID;
    }
        
    public void setRoleID(String  roleID)
    {
        this.roleID=roleID;
    }
    public short  getObjectType()
    {
        return  this.objectType;
    }
        
    public void setObjectType(short  objectType)
    {
        this.objectType=objectType;
    }
    public String  getObjectID()
    {
        return  this.objectID;
    }
        
    public void setObjectID(String  objectID)
    {
        this.objectID=objectID;
    }
}
