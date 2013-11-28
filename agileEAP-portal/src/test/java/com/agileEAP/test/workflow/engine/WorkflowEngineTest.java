package com.agileEAP.test.workflow.engine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.agileEAP.utils.JsonConvert;
import com.agileEAP.workflow.definition.*;
import com.agileEAP.workflow.definition.Transition;
import com.agileEAP.security.entity.Operator;
import com.agileEAP.workflow.engine.*;
import com.agileEAP.workflow.engine.utility.WFUtil;
import com.agileEAP.workflow.entity.*;
import com.agileEAP.workflow.entity.Participant;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@DirtiesContext
@ActiveProfiles("development")
@ContextConfiguration(locations = { "/applicationContext.xml"})//, "/workflow/applicationContext-workflow.xml" })
public class WorkflowEngineTest extends AbstractJUnit4SpringContextTests
{
	@Autowired
	private IWorkflowEngine workflowEngine;
	
	@Test
	public final void toXMLTest()
	{
		ProcessDefine processDefine = new ProcessDefine();
		processDefine.setID(UUID.randomUUID().toString());
		processDefine.setName("test");
		
		List<Activity> activities=new ArrayList<Activity>();
		
		StartActivity a=new StartActivity();
		a.setID(UUID.randomUUID().toString());
		a.setName("start");
		a.setActivityType(ActivityType.StartActivity);
		activities.add(a);
		
		ManualActivity m=new ManualActivity();
		m.setID(UUID.randomUUID().toString());
		m.setName("manualActivity");
		m.seteForm("eForm");
		
		ActionURL url=new ActionURL();
		url.setIsSpecifyURL(true);
		url.setURLType(URLType.CustomURL);
		url.setSpecifyURL("workflow/apply/offer");
		
		m.setCustomURL(url);
		
		m.setActivityType(ActivityType.ManualActivity);
		ActivateRule ar=new ActivateRule();
		ar.setActionPattern(ActionPattern.Method);
		ar.setApplicationUri("url");
		m.setActivateRule(ar);
		
		com.agileEAP.workflow.definition.Participant pa=new com.agileEAP.workflow.definition.Participant();
		pa.setAllowAppointParticipants(false);
		pa.setParticipantType(ParticipantType.CustomRegular);
		List<Participantor> ps=new ArrayList<Participantor>();
		Participantor pr=new Participantor();
		pr.setID("001");
		pr.setName("trh");
		pr.setParticipantorType(ParticipantorType.Person);
		pr.setSortOrder(1);
		ps.add(pr);
		
		pr=new Participantor();
		pr.setID("002");
		pr.setName("qlj");
		pr.setParticipantorType(ParticipantorType.Person);
		pr.setSortOrder(2);
		ps.add(pr);
		pa.setParticipantors(ps);
		m.setParticipant(pa);
		
		activities.add(m);
		
		EndActivity e=new EndActivity();
		e.setID(UUID.randomUUID().toString());
		e.setName("end");
		e.setActivityType(ActivityType.EndActivity);
		activities.add(e);
		
		processDefine.setActivities(activities);
		
		List<Transition> transitions=new ArrayList<Transition>();
		Transition  t=new Transition();
		t.setID(UUID.randomUUID().toString());
		t.setSrcActivity("start");
		t.setDestActivity("manualActivity");
		transitions.add(t);
		
		t=new Transition();
		t.setID(UUID.randomUUID().toString());
		t.setSrcActivity("manualActivity");
		t.setDestActivity("end");
		transitions.add(t);
		
		processDefine.setTransitions(transitions);
		
		String xml=WFUtil.parseProcessDefineToXML(processDefine);
		
		ProcessDefine processDefine2 =WFUtil.parseProcessDefine(xml);
		assertEquals(processDefine.getID(), processDefine2.getID());
		
		String json=processDefine.toJson();
		processDefine2=ProcessDefine.parseFromJson(json);
		assertEquals(processDefine, processDefine2);
	}
	
	
	@Test
	public final void GetProcessDefineTest()
	{
		String processDefID = "02e2fd86-39f3-446b-9a9e-a06700bde8a4";
		ProcessDefine processDefine = workflowEngine.GetProcessDefine(processDefID);
		assertNotNull(processDefine);
	}
		
	@Test
	public final void GetActiveWorkItemsTest()
	{
		String processInstID = "4c571b40-0732-4d0c-8434-a08400b24258";
		Map<WorkItem, List<Operator>> value = workflowEngine.GetActiveWorkItems(processInstID);

		assertNotNull(value);
	}

	@Test
	public final void ErrorActivityInstTest()
	{
		String processInstID = "1e5438f0-9ef6-4bc3-8fe5-a0b000f97ef9";
		workflowEngine.ErrorActivityInst(processInstID);
	}

	@Test
	public final void FormJsonTest()
	{
		String processDefID = "02e2fd86-39f3-446b-9a9e-a06700bde8a4";
		ProcessDefine processDefine = workflowEngine.GetProcessDefine(processDefID);

		JsonConvert jsonConvert=new JsonConvert();
		String json = jsonConvert.toJson(processDefine);
		ProcessDefine processDefine2 = jsonConvert.fromJson(json, ProcessDefine.class);

		assertEquals(processDefine, processDefine2);
	}


//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	@Test
	public final void ActivityConvertTest()
	{
		String processDefID = "02e2fd86-39f3-446b-9a9e-a06700bde8a4";
		ProcessDefine processDefine = workflowEngine.GetProcessDefine(processDefID);
		JsonConvert jsonConvert=new JsonConvert();
		
		String json = jsonConvert.toJson(processDefine);
		ProcessDefine processDefine2 = jsonConvert.fromJson(json, ProcessDefine.class);
		assertEquals(processDefine.getName() , processDefine2.getName());
	}
}