package com.agileEAP.workflow.engine;

public class WorkItemExecutedEvent implements IEventUUID
{
	private WorkItemContext context;
	public final WorkItemContext getContext()
	{
		return context;
	}
	public final void setContext(WorkItemContext value)
	{
		context = value;
	}

	private String uuid;
	public final String getUUID()
	{
		return uuid;
	}
	public final void setUUID(String value)
	{
		uuid = value;
	}
}