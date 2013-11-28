package com.agileEAP.security.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 操作
* @author trh
*/
public  class Operate {
    /**
    * 主键
    */
    private String id;       

    /**
    * 操作名称
    */
    private String name;
    /**
    * 命令
    */
    private String command;
    /**
    * 命令参数
    */
    private String argument;
    /**
    * 是否验证
    */
    private short isVerify;
    /**
    *序号
    */
    private int sortOrder;
    
    public String  getId()
    {
        return  this.id;
    }
        
    public void setId(String  id)
    {
        this.id=id;
    }       
        
    public String  getName()
    {
        return  this.name;
    }
        
    public void setName(String  name)
    {
        this.name=name;
    }
    public String  getCommand()
    {
        return  this.command;
    }
        
    public void setCommand(String  command)
    {
        this.command=command;
    }
    public String  getArgument()
    {
        return  this.argument;
    }
        
    public void setArgument(String  argument)
    {
        this.argument=argument;
    }
    public short  getIsVerify()
    {
        return  this.isVerify;
    }
        
    public void setIsVerify(short  isVerify)
    {
        this.isVerify=isVerify;
    }

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
}
