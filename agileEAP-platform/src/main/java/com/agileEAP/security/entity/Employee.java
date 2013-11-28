package com.agileEAP.security.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 人员信息表
* @author trh
*/
public  class Employee {
    /**
    * 主键
    */
    private String id;
        
    /**
    * 员工编号
    */
    private String code;
    /**
    * 登陆名
    */
    private String loginName;
    /**
    * 人员姓名
    */
    private String name;
    /**
    * 操作员ID
    */
    private String operatorID;
    /**
    * 性别
    */
    private short gender;
    /**
    * 出生日期
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    /**
    * 民族
    */
    private String nation;
    /**
    * 出生地
    */
    private String birthplace;
    /**
    * 籍贯
    */
    private String nativeplace;
    /**
    * 政治面貌
    */
    private String politicsStatus;
    /**
    * 参加工作时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date workFromDate;
    /**
    * 健康状况
    */
    private String healthStatus;
    /**
    * 专业技术职务
    */
    private String industrialGrade;
    /**
    * 特长
    */
    private String speciality;
    /**
    * 岗位名称
    */
    private String positionName;
    /**
    * 基本岗位
    */
    private String position;
    /**
    * 岗位等级
    */
    private String postGrade;
    /**
    * 状态
    */
    private short status;
    /**
    * 证件类型
    */
    private short cardType;
    /**
    * 证件号码
    */
    private String cardNo;
    /**
    * 入职日期
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date inDate;
    /**
    * 离职日期
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date outDate;
    /**
    * 邮编
    */
    private String zipCode;
    /**
    * Email
    */
    private String email;
    /**
    * 传真号码
    */
    private String fax;
    /**
    * 手机号码
    */
    private String mobile;
    /**
    * MSN号码
    */
    private String msn;
    /**
    * 办公电话
    */
    private String officePhone;
    /**
    * 住址
    */
    private String address;
    /**
    * 直接主管
    */
    private String director;
    /**
    * 主机构ID
    */
    private String orgID;
    /**
    * 照片
    */
    private String photo;
    /**
    * 创建者
    */
    private String creator;
    /**
    * 创建时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
    * 公司ID
    */
    private String corpID;
    /**
    * 序号
    */
    private int sortOrder;
    /**
    * 组织路径
    */
    private String orgPath;

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
    public String  getOperatorID()
    {
        return  this.operatorID;
    }
        
    public void setOperatorID(String  operatorID)
    {
        this.operatorID=operatorID;
    }
    public short  getGender()
    {
        return  this.gender;
    }
        
    public void setGender(short  gender)
    {
        this.gender=gender;
    }
    public Date  getBirthday()
    {
        return  this.birthday;
    }
        
    public void setBirthday(Date  birthday)
    {
        this.birthday=birthday;
    }
    public String  getNation()
    {
        return  this.nation;
    }
        
    public void setNation(String  nation)
    {
        this.nation=nation;
    }
    public String  getBirthplace()
    {
        return  this.birthplace;
    }
        
    public void setBirthplace(String  birthplace)
    {
        this.birthplace=birthplace;
    }
    public String  getNativeplace()
    {
        return  this.nativeplace;
    }
        
    public void setNativeplace(String  nativeplace)
    {
        this.nativeplace=nativeplace;
    }
    public String  getPoliticsStatus()
    {
        return  this.politicsStatus;
    }
        
    public void setPoliticsStatus(String  politicsStatus)
    {
        this.politicsStatus=politicsStatus;
    }
    public Date  getWorkFromDate()
    {
        return  this.workFromDate;
    }
        
    public void setWorkFromDate(Date  workFromDate)
    {
        this.workFromDate=workFromDate;
    }
    public String  getHealthStatus()
    {
        return  this.healthStatus;
    }
        
    public void setHealthStatus(String  healthStatus)
    {
        this.healthStatus=healthStatus;
    }
    public String  getIndustrialGrade()
    {
        return  this.industrialGrade;
    }
        
    public void setIndustrialGrade(String  industrialGrade)
    {
        this.industrialGrade=industrialGrade;
    }
    public String  getSpeciality()
    {
        return  this.speciality;
    }
        
    public void setSpeciality(String  speciality)
    {
        this.speciality=speciality;
    }
    public String  getPositionName()
    {
        return  this.positionName;
    }
        
    public void setPositionName(String  positionName)
    {
        this.positionName=positionName;
    }
    public String  getPosition()
    {
        return  this.position;
    }
        
    public void setPosition(String  position)
    {
        this.position=position;
    }
    public String  getPostGrade()
    {
        return  this.postGrade;
    }
        
    public void setPostGrade(String  postGrade)
    {
        this.postGrade=postGrade;
    }
    public short  getstatus()
    {
        return  this.status;
    }
        
    public void setstatus(short  status)
    {
        this.status=status;
    }
    public short  getCardType()
    {
        return  this.cardType;
    }
        
    public void setCardType(short  cardType)
    {
        this.cardType=cardType;
    }
    public String  getCardNo()
    {
        return  this.cardNo;
    }
        
    public void setCardNo(String  cardNo)
    {
        this.cardNo=cardNo;
    }
    public Date  getInDate()
    {
        return  this.inDate;
    }
        
    public void setInDate(Date  inDate)
    {
        this.inDate=inDate;
    }
    public Date  getOutDate()
    {
        return  this.outDate;
    }
        
    public void setOutDate(Date  outDate)
    {
        this.outDate=outDate;
    }
    public String  getZipCode()
    {
        return  this.zipCode;
    }
        
    public void setZipCode(String  zipCode)
    {
        this.zipCode=zipCode;
    }
    public String  getEmail()
    {
        return  this.email;
    }
        
    public void setEmail(String  email)
    {
        this.email=email;
    }
    public String  getFax()
    {
        return  this.fax;
    }
        
    public void setFax(String  fax)
    {
        this.fax=fax;
    }
    public String  getMobile()
    {
        return  this.mobile;
    }
        
    public void setMobile(String  mobile)
    {
        this.mobile=mobile;
    }
    public String  getMsn()
    {
        return  this.msn;
    }
        
    public void setMsn(String  msn)
    {
        this.msn=msn;
    }
    public String  getOfficePhone()
    {
        return  this.officePhone;
    }
        
    public void setOfficePhone(String  officePhone)
    {
        this.officePhone=officePhone;
    }
    public String  getaddress()
    {
        return  this.address;
    }
        
    public void setaddress(String  address)
    {
        this.address=address;
    }
    public String  getDirector()
    {
        return  this.director;
    }
        
    public void setDirector(String  director)
    {
        this.director=director;
    }
    public String  getOrgID()
    {
        return  this.orgID;
    }
        
    public void setOrgID(String  orgID)
    {
        this.orgID=orgID;
    }
    public String  getPhoto()
    {
        return  this.photo;
    }
        
    public void setPhoto(String  photo)
    {
        this.photo=photo;
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
    public String  getCorpID()
    {
        return  this.corpID;
    }
        
    public void setCorpID(String  corpID)
    {
        this.corpID=corpID;
    }
    public int  getSortOrder()
    {
        return  this.sortOrder;
    }
        
    public void setSortOrder(int  sortOrder)
    {
        this.sortOrder=sortOrder;
    }
    public String  getOrgPath()
    {
        return  this.orgPath;
    }
        
    public void setOrgPath(String  orgPath)
    {
        this.orgPath=orgPath;
    }
}
