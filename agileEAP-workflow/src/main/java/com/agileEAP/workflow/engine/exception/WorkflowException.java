package com.agileEAP.workflow.engine.exception;

public class WorkflowException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -397157736100496107L;
	
	public WorkflowException(String message)
	{
		 super(message);
	}

	public WorkflowException(String message,Throwable cause)
	{
		 super(message,cause);
	}
}