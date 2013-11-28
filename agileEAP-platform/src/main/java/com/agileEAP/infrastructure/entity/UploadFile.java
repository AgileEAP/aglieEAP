package com.agileEAP.infrastructure.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 上传文件
* @author trh
*/
public  class UploadFile {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 文件名称
    */
    private String fileName;
    /**
    * 唯一名称
    */
    private String uniqueName;
    /**
    * 文件类型
    */
    private short fileType;
    /**
    * 所在目录
    */
    private String catalogID;
    /**
    * 文件路径
    */
    private String filePath;
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
        
    public String  getFileName()
    {
        return  this.fileName;
    }
        
    public void setFileName(String  fileName)
    {
        this.fileName=fileName;
    }
    public String  getUniqueName()
    {
        return  this.uniqueName;
    }
        
    public void setUniqueName(String  uniqueName)
    {
        this.uniqueName=uniqueName;
    }
    public short  getFileType()
    {
        return  this.fileType;
    }
        
    public void setFileType(short  fileType)
    {
        this.fileType=fileType;
    }
    public String  getCatalogID()
    {
        return  this.catalogID;
    }
        
    public void setCatalogID(String  catalogID)
    {
        this.catalogID=catalogID;
    }
    public String  getFilePath()
    {
        return  this.filePath;
    }
        
    public void setFilePath(String  filePath)
    {
        this.filePath=filePath;
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
