/**
* @author trh
*/
package com.agileEAP.infrastructure.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public  class ActionLog {
private String id;
private String userName;
private short logType;
private String clientIP;
private String content;
private String appModule;
private String message;
private short result;
private String userID;
private Date createTime;

public String  getId()
{
    return  this.id;
}
    
public void setId(String  id)
{
    this.id=id;
}        
    
public String  getUserName()
{
    return  this.userName;
}
    
public void setUserName(String  userName)
{
    this.userName=userName;
}
public short  getLogType()
{
    return  this.logType;
}
    
public void setLogType(short  logType)
{
    this.logType=logType;
}
public String  getClientIP()
{
    return  this.clientIP;
}
    
public void setClientIP(String  clientIP)
{
    this.clientIP=clientIP;
}
public String  getContent()
{
    return  this.content;
}
    
public void setContent(String  content)
{
    this.content=content;
}
public String  getAppModule()
{
    return  this.appModule;
}
    
public void setAppModule(String  appModule)
{
    this.appModule=appModule;
}
public String  getMessage()
{
    return  this.message;
}
    
public void setMessage(String  message)
{
    this.message=message;
}
public short  getResult()
{
    return  this.result;
}
    
public void setResult(short  result)
{
    this.result=result;
}
public String  getUserID()
{
    return  this.userID;
}
    
public void setUserID(String  userID)
{
    this.userID=userID;
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
