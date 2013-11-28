package com.agileEAP.workflow.engine;

/** 
 把Engine所需要的服务的接口都定义在一个接口中，便于使用和管理
 
*/
public interface IEngineService
{
	IWorkflowPersistence getPersistence();
	void setPersistence(IWorkflowPersistence value);
	INotification getNotification();
	void setNotification(INotification value);
	IWorkAssign getWorkAssign();
	void setWorkAssign(IWorkAssign value);
}