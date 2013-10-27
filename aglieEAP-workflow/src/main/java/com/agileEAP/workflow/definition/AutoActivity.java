package com.agileEAP.workflow.definition;

//[Remark("自动活动")]
public class AutoActivity extends Activity
{

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
	 执行动作
	 
	*/
	private String executeAction;
	public final String getExecuteAction()
	{
		return executeAction;
	}
	public final void setExecuteAction(String value)
	{
		executeAction= value;
	}

	/** 
	 回滚操作
	 
	*/

	private CustomAction privateRollBack;
	public final CustomAction getRollBack()
	{
		return privateRollBack;
	}
	public final void setRollBack(CustomAction value)
	{
		privateRollBack = value;
	}
}