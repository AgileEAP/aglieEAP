package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

//[Remark("开始活动")]
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="activity")
public class StartActivity extends Activity
{
	/** 
	 工作流表单
	 
	*/
	private String eForm;
	public final String geteForm()
	{
		return eForm;
	}
	public final void seteForm(String value)
	{
		eForm= value;
	}
}