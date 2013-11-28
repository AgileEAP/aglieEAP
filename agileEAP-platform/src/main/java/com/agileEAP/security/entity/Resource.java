package com.agileEAP.security.entity;

import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 资源
* @author trh
*/
public  class Resource {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 资源名称
    */
    private String name;
    /**
    * 资源类型
    */
    private short type;
    /**
    * 序号
    */
    private int sortOrder;
    /**
    * 父资源
    */
    private String parentID;
    /**
    * 调用入口
    */
    private String entry;
    /**
    * URL地址
    */
    private String url;
    /**
    * 图标
    */
    private String icon;
    /**
    * 显示模式
    */
    private short openMode;
    /**
    * 显示导航
    */
    private short showNavigation;
    /**
    * 显示工具栏
    */
    private short showToolBar;

    private String parentName;
    
    private boolean authorized;
    
    private String navigationTitle;
    
    private List<Operate> operates;
    
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
    public short  getType()
    {
        return  this.type;
    }
        
    public void setType(short  type)
    {
        this.type=type;
    }
    public int  getSortOrder()
    {
        return  this.sortOrder;
    }
        
    public void setSortOrder(int  sortOrder)
    {
        this.sortOrder=sortOrder;
    }
    public String  getParentID()
    {
        return  this.parentID;
    }
        
    public void setParentID(String  parentID)
    {
        this.parentID=parentID;
    }
    public String  getEntry()
    {
        return  this.entry;
    }
        
    public void setEntry(String  entry)
    {
        this.entry=entry;
    }
    public String  getUrl()
    {
        return  this.url;
    }
        
    public void setUrl(String  url)
    {
        this.url=url;
    }
    public String  getIcon()
    {
        return  this.icon;
    }
        
    public void setIcon(String  icon)
    {
        this.icon=icon;
    }
    public short  getOpenMode()
    {
        return  this.openMode;
    }
        
    public void setOpenMode(short  openMode)
    {
        this.openMode=openMode;
    }
    public short  getShowNavigation()
    {
        return  this.showNavigation;
    }
        
    public void setShowNavigation(short  showNavigation)
    {
        this.showNavigation=showNavigation;
    }
    public short  getShowToolBar()
    {
        return  this.showToolBar;
    }
        
    public void setShowToolBar(short  showToolBar)
    {
        this.showToolBar=showToolBar;
    }

	public List<Operate> getOperates() {
		return operates;
	}

	public void setOperates(List<Operate> operates) {
		this.operates = operates;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getNavigationTitle() {
		return navigationTitle;
	}

	public void setNavigationTitle(String navigationTitle) {
		this.navigationTitle = navigationTitle;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
}
