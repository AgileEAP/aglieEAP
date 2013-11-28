package com.agileEAP.infrastructure.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 文件目录
* @author trh
*/
public  class Catalog {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 目录名称
    */
    private String catalogName;
    /**
    * 目录类型
    */
    private short catalogType;
    /**
    * 上级目录
    */
    private String parentID;
    /**
    * 目录路径
    */
    private String path;
    /**
    * 序号
    */
    private int sortOrder;
    /**
    * 描述
    */
    private String description;
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
        
    public String  getCatalogName()
    {
        return  this.catalogName;
    }
        
    public void setCatalogName(String  catalogName)
    {
        this.catalogName=catalogName;
    }
    public short  getCatalogType()
    {
        return  this.catalogType;
    }
        
    public void setCatalogType(short  catalogType)
    {
        this.catalogType=catalogType;
    }
    public String  getParentID()
    {
        return  this.parentID;
    }
        
    public void setParentID(String  parentID)
    {
        this.parentID=parentID;
    }
    public String  getPath()
    {
        return  this.path;
    }
        
    public void setPath(String  path)
    {
        this.path=path;
    }
    public int  getSortOrder()
    {
        return  this.sortOrder;
    }
        
    public void setSortOrder(int  sortOrder)
    {
        this.sortOrder=sortOrder;
    }
    public String  getDescription()
    {
        return  this.description;
    }
        
    public void setDescription(String  description)
    {
        this.description=description;
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
}
