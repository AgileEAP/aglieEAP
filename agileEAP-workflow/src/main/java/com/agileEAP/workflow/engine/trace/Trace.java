package com.agileEAP.workflow.engine.trace;

import com.agileEAP.workflow.entity.TraceLog;
import com.agileEAP.workflow.engine.exception.WorkflowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 跟踪日志
 
*/
public class Trace
{
	private static Logger logger = LoggerFactory.getLogger(Trace.class);
	/** 
	 跟踪日志
	 
	 @param message 消息
	*/
	public static void Print(String message)
	{
		Print(message, null);
	}

	/** 
	 跟踪日志
	 
	 @param message 消息
	*/
	public static void Print(WorkflowException exception)
	{
		Print(exception.getMessage(), null);
	}

	/** 
	 跟踪日志
	 
	 @param message
	 @param ex
	*/
	public static void Print(String message, RuntimeException ex)
	{
		logger.info(message, ex);
	}

	/** 
	 跟踪日志
	 
	 @param log 日志记录
	*/
	public static void Print(TraceLog log)
	{

	}
}