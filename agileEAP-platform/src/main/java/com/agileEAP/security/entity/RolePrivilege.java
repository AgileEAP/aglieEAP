package com.agileEAP.security.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 角色与权限
* @author trh
*/
public  class RolePrivilege {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 角色ID
    */
    private String roleID;
    /**
    * 权限ID
    */
    private String privilegeID;

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
    public String  getPrivilegeID()
    {
        return  this.privilegeID;
    }
        
    public void setPrivilegeID(String  privilegeID)
    {
        this.privilegeID=privilegeID;
    }
}
