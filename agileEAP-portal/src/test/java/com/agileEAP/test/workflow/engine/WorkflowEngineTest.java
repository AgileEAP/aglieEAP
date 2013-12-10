package com.agileEAP.test.workflow.engine;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.agileEAP.workflow.definition.Activity;
import com.agileEAP.workflow.definition.EndActivity;
import com.agileEAP.workflow.definition.ProcessDefine;
import com.agileEAP.workflow.definition.StartActivity;
import com.agileEAP.workflow.engine.IWorkflowEngine;

@DirtiesContext
@ActiveProfiles("development")
@ContextConfiguration(locations = { "/applicationContext.xml" })
// , "/workflow/applicationContext-workflow.xml" })
public class WorkflowEngineTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	private IWorkflowEngine workflowEngine;

	@Test
	public final void startWorkflowTest() {
		String processDefID = "46fdd1c2-7aa3-49f8-9f5b-cb6a6ac57ea9";

		String processInstID = workflowEngine.CreateAProcess(processDefID);
		workflowEngine.StartAProcess(processInstID);

		assertNotNull(processInstID);
	}

	@Test
	public final void getStartActivityTest() {
		String processDefID = "46fdd1c2-7aa3-49f8-9f5b-cb6a6ac57ea9";

		ProcessDefine define = workflowEngine.GetProcessDefine(processDefID);
		List<Activity> activities = define.getActivities();

		StartActivity startActivity = define.getStartActivity();
		EndActivity endActivity = define.getEndActivity();

		assertNotNull(define);
		assertNotNull(activities);
		assertNotNull(startActivity);
		assertNotNull(endActivity);
	}
}