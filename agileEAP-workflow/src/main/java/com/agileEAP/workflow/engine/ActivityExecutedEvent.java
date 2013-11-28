package com.agileEAP.workflow.engine;

public class ActivityExecutedEvent implements IEventUUID
{
	private ActivityContext ontext;
	public final ActivityContext getContext()
	{
		return ontext;
	}
	public final void setContext(ActivityContext value)
	{
		ontext = value;
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