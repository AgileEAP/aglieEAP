package com.agileEAP.security.entity;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

/**
* 系统登录用户表
* @author trh
*/
public  class Operator {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 用户类型
    */
    private short userType;
    /**
    * 登录名
    */
    private String loginName;
    /**
    * 姓名
    */
    private String name;
    /**
    * 密码
    */
    private String password;
    /**
    * 盐
    */
    private String salt;
    /**
    * 密码失效时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date expireTime;
    /**
    * 状态
    */
    private short status;
    /**
    * 主题
    */
    private String theme;
    /**
    * 邮箱
    */
    private String email;
    /**
    * 联系电话
    */
    private String phone;
    /**
    * 主机构ID
    */
    private String orgID;
    
    /**
    * 主机构名称
    */
    private String orgName;
    
    /**
    * 公司ID
    */
    private String corpID;
    /**
    * 公司名
    */
    private String corpName;
    /**
    * 创建者
    */
    private String creator;
    /**
    * 创建时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    private List<String> roleList;
    
	public String  getId()
    {
        return  this.id;
    }
        
    public void setId(String  id)
    {
        this.id=id;
    }        
        
    public short  getUserType()
    {
        return  this.userType;
    }
        
    public void setUserType(short  userType)
    {
        this.userType=userType;
    }
    public String  getLoginName()
    {
        return  this.loginName;
    }
        
    public void setLoginName(String  loginName)
    {
        this.loginName=loginName;
    }
    public String  getName()
    {
        return  this.name;
    }
        
    public void setName(String  name)
    {
        this.name=name;
    }
    public String  getPassword()
    {
        return  this.password;
    }
        
    public void setPassword(String  password)
    {
        this.password=password;
    }
    public String  getSalt()
    {
        return  this.salt;
    }
        
    public void setSalt(String  salt)
    {
        this.salt=salt;
    }
    public Date  getExpireTime()
    {
        return  this.expireTime;
    }
        
    public void setExpireTime(Date  expireTime)
    {
        this.expireTime=expireTime;
    }
    public short  getstatus()
    {
        return  this.status;
    }
        
    public void setstatus(short  status)
    {
        this.status=status;
    }
    public String  getTheme()
    {
        return  this.theme;
    }
        
    public void setTheme(String  theme)
    {
        this.theme=theme;
    }
    public String  getEmail()
    {
        return  this.email;
    }
        
    public void setEmail(String  email)
    {
        this.email=email;
    }
    public String  getPhone()
    {
        return  this.phone;
    }
        
    public void setPhone(String  phone)
    {
        this.phone=phone;
    }
    public String  getOrgID()
    {
        return  this.orgID;
    }
        
    public void setOrgID(String  orgID)
    {
        this.orgID=orgID;
    }
    public String  getCorpID()
    {
        return  this.corpID;
    }
        
    public void setCorpID(String  corpID)
    {
        this.corpID=corpID;
    }
    public String  getCorpName()
    {
        return  this.corpName;
    }
        
    public void setCorpName(String  corpName)
    {
        this.corpName=corpName;
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
    
	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}
