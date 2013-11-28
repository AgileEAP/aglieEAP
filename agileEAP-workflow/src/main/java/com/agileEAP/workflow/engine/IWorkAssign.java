package com.agileEAP.workflow.engine;

/** 
 定义工作分配接口
 
*/
public interface IWorkAssign
{
	/** 
	 分配工作项
	 
	 @param workItemID
	*/
	void AssignWorkItem(String workItemID);
}