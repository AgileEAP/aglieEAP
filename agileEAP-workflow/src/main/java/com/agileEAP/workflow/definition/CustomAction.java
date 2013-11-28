package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 自动应用
 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CustomAction 
{
	/** 
	 响应类型
	*/
	private ActionPattern actionPattern= ActionPattern.Method;
	public final ActionPattern getActionPattern()
	{
		return actionPattern;
	}
	public final void setActionPattern(ActionPattern value)
	{
		actionPattern= value;
	}

	/** 
	 事物失败
	 
	*/
	private SuppressJoinFailure suppressJoinFailure= SuppressJoinFailure.Suppress;
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
	private TransactionType transactionType=TransactionType.Join;
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
	private ExceptionStrategy exceptionStrategy=ExceptionStrategy.Rollback;
	public final ExceptionStrategy getExceptionStrategy()
	{
		return exceptionStrategy;
	}
	public final void setExceptionStrategy(ExceptionStrategy value)
	{
		exceptionStrategy= value;
	}

	/** 
	 调用模式
	 
	*/
	private InvokePattern invokePattern=InvokePattern.Synchronous;
	public final InvokePattern getInvokePattern()
	{
		return invokePattern;
	}
	public final void setInvokePattern(InvokePattern value)
	{
		invokePattern=value;
	}


	/** 
	 参数
	 
	*/

	private java.util.ArrayList<Parameter> parameters;
	public final java.util.ArrayList<Parameter> getParameters()
	{
		return parameters;
	}
	public final void setParameters(java.util.ArrayList<Parameter> value)
	{
		parameters = value;
	}
}