package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/** 
 激活规则 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ActivateRule 
{
	private ActivateRuleType activateRuleType;
	/** 
	 激活类型
	*/
	public  ActivateRuleType getActivateRuleType()
	{
		return activateRuleType;
	}
	public void setActivateRuleType(ActivateRuleType value)
	{
		activateRuleType= value;
	}

	private ActionPattern actionPattern;
	/** 
	 响应类型
	*/
	public  ActionPattern getActionPattern()
	{
		return actionPattern;
	}
	public  void setActionPattern(ActionPattern value)
	{
		actionPattern= value;
	}

	/** 
	 事物失败
	*/
	private SuppressJoinFailure suppressJoinFailure;
	public final SuppressJoinFailure getSuppressJoinFailure()
	{
		return suppressJoinFailure;
	}
	public final void setSuppressJoinFailure(SuppressJoinFailure value)
	{
		suppressJoinFailure= value;
	}

	/** 
	 应用Uri	 
	*/
	private String applicationUri;
	public final String getApplicationUri()
	{
		return applicationUri;
	}
	public final void setApplicationUri(String value)
	{
		applicationUri=value;
	}

	/** 
	事物类型
	*/
	private TransactionType transactionType;
	public final TransactionType getTransactionType()
	{
		return transactionType;
	}
	public final void setTransactionType(TransactionType value)
	{
		transactionType= value;
	}

	/** 
	 异常处理策略
	*/
	private ExceptionStrategy exceptionStrategy;
	public final ExceptionStrategy getExceptionStrategy()
	{
		return exceptionStrategy;
	}
	public final void setExceptionStrategy(ExceptionStrategy value)
	{
		exceptionStrategy=value;
	}

	/** 
	 调用模式
	 
	*/
	private InvokePattern invokePattern;
	public final InvokePattern getInvokePattern()
	{
		return invokePattern;
	}
	public final void setInvokePattern(InvokePattern value)
	{
		invokePattern= value;
	}

	/** 
	 规则逻辑
	 
	*/
	private String activateRuleApp;
	public final String getActivateRuleApp()
	{
		return activateRuleApp;
	}
	public final void setActivateRuleApp(String value)
	{
		activateRuleApp= value;
	}

	/** 
	 参数
	*/
	private java.util.ArrayList<Parameter> privateParameters;
	public final java.util.ArrayList<Parameter> getParameters()
	{
		return privateParameters;
	}
	public final void setParameters(java.util.ArrayList<Parameter> value)
	{
		privateParameters = value;
	}
}