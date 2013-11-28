package com.agileEAP.workflow.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

//[Remark("子流程")]
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class SubflowActivity extends Activity
{

	/** 
	 调用方式，synchronous同步，asynchronous异步
	 
	*/
	private InvokePattern invokePattern = InvokePattern.forValue(0);
	public final InvokePattern getInvokePattern()
	{
		return invokePattern;
	}
	public final void setInvokePattern(InvokePattern value)
	{
		invokePattern = value;
	}

	/** 
	 子流程
	 
	*/
	private String subProcess;
	public final String getSubProcess()
	{
		return subProcess;
	}
	public final void setSubProcess(String value)
	{
		subProcess = value;
	}

	/** 
	 是否多子流程
	 
	*/
	private boolean isMultiSubProcess;
	public final boolean getIsMultiSubProcess()
	{
		return isMultiSubProcess;
	}
	public final void setIsMultiSubProcess(boolean value)
	{
		isMultiSubProcess = value;
	}

	/** 
	 关联数据
	 
	*/
	private String iterationRelevantData;
	public final String getIterationRelevantData()
	{
		return iterationRelevantData;
	}
	public final void setIterationRelevantData(String value)
	{
		iterationRelevantData = value;
	}

	/** 
	 关联变量名
	 
	*/
	private String iterationVariableName;
	public final String getIterationVariableName()
	{
		return iterationVariableName;
	}
	public final void setIterationVariableName(String value)
	{
		iterationVariableName = value;
	}


	/** 
	 激动规则
	 
	*/
	private ActivateRule activateRule;
	public final ActivateRule getActivateRule()
	{
		return activateRule;
	}
	public final void setActivateRule(ActivateRule value)
	{
		activateRule = value;
	}

	/** 
	 回滚操作
	 
	*/
	private CustomAction rollBack;
	public final CustomAction getRollBack()
	{
		return rollBack;
	}
	public final void setRollBack(CustomAction value)
	{
		rollBack = value;
	}
}