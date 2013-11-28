package com.agileEAP.workflow.engine;

public interface IWorkflowService
{
	String GetProcessDefine(String processDefID);

	String GetProcessActivityInsts(String processInstID);

	String GetProcessTransitions(String processInstID);
}