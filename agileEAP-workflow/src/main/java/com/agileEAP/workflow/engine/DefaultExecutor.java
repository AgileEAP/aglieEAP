package com.agileEAP.workflow.engine;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.agileEAP.workflow.entity.WorkItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultExecutor {
	private static Logger logger = LoggerFactory.getLogger(DefaultExecutor.class);

	public static boolean Execute(String uuid, WorkItem wi) {
		WebApplicationContext wb = WebApplicationContextUtils
				.getWebApplicationContext(null);// (getServletContext());
		IAutoActivityHandler handler = (IAutoActivityHandler) wb.getBean(uuid);
		// com.agileEAP.core.Infrastructure.EngineContext.Current.<IAutoActivityHandler>Resolve(uuid);
		if (handler != null) {
			try {
				return handler.Execute(wi);
			} catch (RuntimeException ex) {
				logger.error(String.format("执行自动活动%1$s自动处理%2$s出错", uuid,
						handler.getClass().getName()), ex);
				throw ex;
			}
		}

		throw new RuntimeException(String.format(
				"活动%1$s没有注册%2$s处理IAutoActivityHandler",
				wi.getActivityInstName(), uuid));
	}
}