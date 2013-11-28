package com.agileEAP.workflow.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.agileEAP.data.RequestPageData;
import com.agileEAP.utils.JsonConvert;

public class EventPublisher
{
	private static Logger logger = LoggerFactory.getLogger(EventPublisher.class);
	
	public static <TEvent extends IEventUUID> void Publish(TEvent eventMessage)
	{
		String uuid = eventMessage.getUUID();
		//   TODO 
		WebApplicationContext wb = WebApplicationContextUtils.getWebApplicationContext(null);//(getServletContext());
		IConsumer<TEvent> consumer =(IConsumer<TEvent> )wb.getBean(uuid);
		//IConsumer<TEvent> consumer = com.agileEAP.core.Infrastructure.EngineContext.Current.<IConsumer<TEvent>>Resolve(uuid);
		if (consumer != null)
		{
			try
			{
				consumer.HandleEvent(eventMessage);
			}
			catch (RuntimeException ex)
			{
		    	JsonConvert jsonConvert = new JsonConvert();
				//logger.error(String.format("execute IConsumer<%1$s> HandleEvent error message:%2$s ", TEvent.class), jsonConvert.toJson(eventMessage), ex);

				throw ex;
			}
		}
	}
}