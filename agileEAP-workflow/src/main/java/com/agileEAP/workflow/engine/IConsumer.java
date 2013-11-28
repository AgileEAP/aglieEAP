package com.agileEAP.workflow.engine;

public interface IConsumer<TEvent>
{
	void HandleEvent(TEvent eventMessage);
}