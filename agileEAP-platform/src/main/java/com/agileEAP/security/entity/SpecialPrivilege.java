package com.agileEAP.security.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 针对人员配置的特殊权限，如特别开通的功能，或者特别禁止的功能
* @author trh
*/
public  class SpecialPrivilege {
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
    * 1：特别开通，2：特别禁止
    */
    private short authFlag;
    /**
    * 冗余字段
    */
    private String appID;
    /**
    * 生效时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
    * 失效时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
    * 序号
    */
    private int sortOrder;
    /**
    * 授权人
    */
    private String authorizer;
    /**
    * 授权时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date authTime;

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
    public short  getAuthFlag()
    {
        return  this.authFlag;
    }
        
    public void setAuthFlag(short  authFlag)
    {
        this.authFlag=authFlag;
    }
    public String  getAppID()
    {
        return  this.appID;
    }
        
    public void setAppID(String  appID)
    {
        this.appID=appID;
    }
    public Date  getStartTime()
    {
        return  this.startTime;
    }
        
    public void setStartTime(Date  startTime)
    {
        this.startTime=startTime;
    }
    public Date  getEndTime()
    {
        return  this.endTime;
    }
        
    public void setEndTime(Date  endTime)
    {
        this.endTime=endTime;
    }
    public int  getSortOrder()
    {
        return  this.sortOrder;
    }
        
    public void setSortOrder(int  sortOrder)
    {
        this.sortOrder=sortOrder;
    }
    public String  getAuthorizer()
    {
        return  this.authorizer;
    }
        
    public void setAuthorizer(String  authorizer)
    {
        this.authorizer=authorizer;
    }
    public Date  getAuthTime()
    {
        return  this.authTime;
    }
        
    public void setAuthTime(Date  authTime)
    {
        this.authTime=authTime;
    }
}
