package com.agileEAP.workflow.definition;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="activity")
public class ManualActivity extends Activity
{
	/** 
	 是否允许代理
	 
	*/
	private boolean allowAgent;
	public final boolean getAllowAgent()
	{
		return allowAgent;
	}
	public final void setAllowAgent(boolean value)
	{
		allowAgent= value;
	}

	/** 
	 重启参与者
	 
	*/
	private ResetParticipant resetParticipant=ResetParticipant.FirstParticipantor;
	public final ResetParticipant getResetParticipant()
	{
		return resetParticipant;
	}
	public final void setResetParticipant(ResetParticipant value)
	{
		resetParticipant= value;
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
	 扩展属性，自定义URL
	 
	*/

	private ActionURL customURL;
	public final ActionURL getCustomURL()
	{
		return customURL;
	}
	public final void setCustomURL(ActionURL value)
	{
		customURL = value;
	}

	/** 
	 重置URL
	 
	*/

	private ActionURL resetURL;
	public final ActionURL getResetURL()
	{
		return resetURL;
	}
	public final void setResetURL(ActionURL value)
	{
		resetURL = value;
	}

	/** 
	 参与者
	 
	*/

	private Participant participant;
	public final Participant getParticipant()
	{
		return participant;
	}
	public final void setParticipant(Participant value)
	{
		participant = value;
	}

	/** 
	 多工作项设置
	 
	*/

	private MultiWorkItem multiWorkItem;
	public final MultiWorkItem getMultiWorkItem()
	{
		return multiWorkItem;
	}
	public final void setMultiWorkItem(MultiWorkItem value)
	{
		multiWorkItem = value;
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

	/** 
	 自由流规则
	 
	*/

	private FreeFlowRule freeFlowRule;
	public final FreeFlowRule getFreeFlowRule()
	{
		return freeFlowRule;
	}
	public final void setFreeFlowRule(FreeFlowRule value)
	{
		freeFlowRule = value;
	}

	/** 
	 工作流表单
	 
	*/
	private String eForm=UUID.randomUUID().toString();
	public final String geteForm()
	{
		return eForm;
	}
	public final void seteForm(String value)
	{
		eForm= value;
	}
}