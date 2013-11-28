package com.agileEAP.workflow.engine;


public class ActivityExecutingEvent implements IEventUUID
{
	private ActivityContext context;
	public final ActivityContext getContext()
	{
		return context;
	}
	public final void setContext(ActivityContext value)
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