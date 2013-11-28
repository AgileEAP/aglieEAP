package com.agileEAP.security.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 组织机构 
* @author trh
*/
public  class Organization {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 机构代码
    */
    private String code;
    /**
    * 上级组织
    */
    private String parentID;
    
    /**
    * 上级组织名称
    */
    private String parentName;
    
    /**
    * 机构名称
    */
    private String name;
    /**
    * 总公司/总部部门/分公司/分公司部门
    */
    private int grade;
    /**
    * 序号
    */
    private int sortOrder;
    /**
    * 创建者
    */
    private String creator;
    /**
    * 创建时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public String  getId()
    {
        return  this.id;
    }
        
    public void setId(String  id)
    {
        this.id=id;
    }        
        
    public String  getCode()
    {
        return  this.code;
    }
        
    public void setCode(String  code)
    {
        this.code=code;
    }
    public String  getParentID()
    {
        return  this.parentID;
    }
        
    public void setParentID(String  parentID)
    {
        this.parentID=parentID;
    }
    public String  getName()
    {
        return  this.name;
    }
        
    public void setName(String  name)
    {
        this.name=name;
    }
    public int  getGrade()
    {
        return  this.grade;
    }
        
    public void setGrade(int  grade)
    {
        this.grade=grade;
    }   

    public int  getSortOrder()
    {
        return  this.sortOrder;
    }
        
    public void setSortOrder(int  sortOrder)
    {
        this.sortOrder=sortOrder;
    }
    public String  getCreator()
    {
        return  this.creator;
    }
        
    public void setCreator(String  creator)
    {
        this.creator=creator;
    }
    public Date  getCreateTime()
    {
        return  this.createTime;
    }
        
    public void setCreateTime(Date  createTime)
    {
        this.createTime=createTime;
    }

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
