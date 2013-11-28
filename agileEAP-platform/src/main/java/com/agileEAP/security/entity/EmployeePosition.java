package com.agileEAP.security.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 定义人员和岗位的对应关系，需要注明，一个人员可以设定一个基本岗位
* @author trh
*/
public  class EmployeePosition {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 岗位编号
    */
    private String positionID;
    /**
    * 人员编号
    */
    private String employeeID;
    /**
    * 是否主岗位
    */
    private short isMajor;

    public String  getId()
    {
        return  this.id;
    }
        
    public void setId(String  id)
    {
        this.id=id;
    }        
        
    public String  getPositionID()
    {
        return  this.positionID;
    }
        
    public void setPositionID(String  positionID)
    {
        this.positionID=positionID;
    }
    public String  getEmployeeID()
    {
        return  this.employeeID;
    }
        
    public void setEmployeeID(String  employeeID)
    {
        this.employeeID=employeeID;
    }
    public short  getIsMajor()
    {
        return  this.isMajor;
    }
        
    public void setIsMajor(short  isMajor)
    {
        this.isMajor=isMajor;
    }
}
