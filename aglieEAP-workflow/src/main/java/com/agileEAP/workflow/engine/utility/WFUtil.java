package com.agileEAP.workflow.engine.utility;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.eclipse.persistence.jaxb.JAXBContextFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.shiro.SecurityUtils;
import com.agileEAP.security.service.ShiroDbRealm.ShiroUser;
import com.agileEAP.workflow.definition.Activity;
import com.agileEAP.workflow.definition.AutoActivity;
import com.agileEAP.workflow.definition.EndActivity;
import com.agileEAP.workflow.definition.ManualActivity;
import com.agileEAP.workflow.definition.ProcessDefine;
import com.agileEAP.workflow.definition.RouterActivity;
import com.agileEAP.workflow.definition.StartActivity;
import com.agileEAP.workflow.definition.SubflowActivity;
import com.agileEAP.workflow.engine.exception.WorkflowException;
import com.agileEAP.workflow.engine.trace.Trace;

/**
 * 工作流上下文
 */
public final class WFUtil {
	private final static Logger logger = LoggerFactory.getLogger(WFUtil.class);

	/**
	 * Gets or sets the current user
	 */
	public static ShiroUser getUser() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

		return user;
	}

	/**
	 * 默认最小时间
	 */
	public final static java.util.Date minDate = new java.util.Date(2000, 1, 1);

	/**
	 * 默认最大时间
	 */
	public final static java.util.Date MaxDate = new java.util.Date(2099, 1, 1);

	/**
	 * 默认变量前缀
	 */
	public final static char ExpressionVariablePrefix = ':';

	/**
	 * 处理异常
	 * 
	 * @param exception
	 */
	public static void HandleException(WorkflowException exception) {
		Trace.Print(exception);
		throw exception;
	}

	public static Activity getActivity(List<Activity> activities,
			String activityDefID) {
		if (activities == null)
			return null;

		for (Activity activity : activities) {
			if (activity.getID().equals(activityDefID)) {
				return activity;
			}
		}

		return null;
	}

	public static ProcessDefine parseProcessDefine(String xml) {
		JAXBContext jaxbContext;
		try {
/*			jaxbContext = JAXBContext
					.newInstance(new Class[] { ProcessDefine.class,
							StartActivity.class, ManualActivity.class,
							AutoActivity.class,SubflowActivity.class,
							RouterActivity.class,EndActivity.class});*/
			//jaxbUnmarshaller.setProperty("javax.xml.bind.context.factory", new org.eclipse.persistence.jaxb.JAXBContextFactory());
			 jaxbContext = JAXBContextFactory.createContext(new Class[] { 
					 ProcessDefine.class,
					 StartActivity.class,
					 ManualActivity.class,
					 AutoActivity.class,
					 SubflowActivity.class,
					 RouterActivity.class,
					 EndActivity.class}, null);

			
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (ProcessDefine) jaxbUnmarshaller.unmarshal(new StringReader(
					xml));
		} catch (JAXBException e) {
			logger.error("init ProcessDefine %1s error " + xml, e);
			e.printStackTrace();
		}
		return null;
	}

	public static String parseProcessDefineToXML(ProcessDefine processDefine) {
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext
					.newInstance(new Class[] { ProcessDefine.class,
							StartActivity.class, ManualActivity.class,
							AutoActivity.class,SubflowActivity.class,
							RouterActivity.class,EndActivity.class});
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			   // for getting nice formatted output
			   jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			   java.io.Writer writer= new java.io.StringWriter();
			   // Writing to XML file
			   jaxbMarshaller.marshal(processDefine, writer); 
			   // Writing to console
			   jaxbMarshaller.marshal(processDefine, System.out); 
			   return writer.toString();
			//return jaxbMarshaller.marshal();
		} catch (JAXBException e) {
			logger.error("ProcessDefine %1s to xml error " + processDefine.getID(), e);
			e.printStackTrace();
		}
		return null;
	}
}