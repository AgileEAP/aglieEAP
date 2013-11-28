package com.agileEAP.workflow.engine;

import com.agileEAP.workflow.entity.WorkItem;

public interface IAutoActivityHandler
{
	boolean Execute(WorkItem wi);
}