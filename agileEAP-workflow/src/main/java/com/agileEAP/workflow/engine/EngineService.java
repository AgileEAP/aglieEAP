package com.agileEAP.workflow.engine;

/** 
 工作流服务类
 
*/
public class EngineService implements IEngineService
{
	public EngineService()
	{
		setPersistence(new WorkflowPersistence());
		setNotification(new Notification());
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IEngineService

	private IWorkflowPersistence privatePersistence;
	public final IWorkflowPersistence getPersistence()
	{
		return privatePersistence;
	}
	public final void setPersistence(IWorkflowPersistence value)
	{
		privatePersistence = value;
	}

	private INotification privateNotification;
	public final INotification getNotification()
	{
		return privateNotification;
	}
	public final void setNotification(INotification value)
	{
		privateNotification = value;
	}

	private IWorkAssign privateWorkAssign;
	public final IWorkAssign getWorkAssign()
	{
		return privateWorkAssign;
	}
	public final void setWorkAssign(IWorkAssign value)
	{
		privateWorkAssign = value;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}