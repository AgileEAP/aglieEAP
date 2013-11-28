package com.agileEAP.security.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 定义人员和机构的关系表
* @author trh
*/
public  class EmployeeOrg {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 机构ID
    */
    private String orgID;
    /**
    * 人员ID
    */
    private String employeeID;

    public String  getId()
    {
        return  this.id;
    }
        
    public void setId(String  id)
    {
        this.id=id;
    }        
        
    public String  getOrgID()
    {
        return  this.orgID;
    }
        
    public void setOrgID(String  orgID)
    {
        this.orgID=orgID;
    }
    public String  getEmployeeID()
    {
        return  this.employeeID;
    }
        
    public void setEmployeeID(String  employeeID)
    {
        this.employeeID=employeeID;
    }
}
