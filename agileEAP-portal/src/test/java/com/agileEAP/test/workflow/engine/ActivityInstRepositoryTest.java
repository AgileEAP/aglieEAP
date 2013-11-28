package com.agileEAP.test.workflow.engine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.agileEAP.utils.Identities;
import com.agileEAP.utils.JsonConvert;
import com.agileEAP.workflow.definition.*;
import com.agileEAP.workflow.definition.Transition;
import com.agileEAP.security.entity.Operator;
import com.agileEAP.workflow.engine.*;
import com.agileEAP.workflow.engine.utility.WFUtil;
import com.agileEAP.workflow.entity.*;
import com.agileEAP.workflow.entity.Participant;
import com.agileEAP.workflow.repository.ActivityInstRepository;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@DirtiesContext
@ActiveProfiles("development")
@ContextConfiguration(locations = { "/applicationContext.xml"})
public class ActivityInstRepositoryTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private ActivityInstRepository activityInstRepository;
	
	@Test
	public final void RepositoryNewTest()
	{
		String testvalue=String.valueOf(Identities.randomLong());
		
		ActivityInst ai=new ActivityInst();
		ai.setActivityDefID(testvalue);
		ai.setCreateTime(new Date());
		ai.setCurrentState((short)1);
		ai.setDescription(testvalue);
		ai.setEndTime(new Date());
		ai.setId(testvalue);
		ai.setName(testvalue);
		ai.setProcessInstID(testvalue);
		ai.setRollbackFlag((short)0);
		ai.setStartTime(new Date());
		ai.setSubProcessInstID(testvalue);
		ai.setType((short)1);
		
		activityInstRepository.save(ai);
		
		String newname="newname";
		ai.setName(newname);
		activityInstRepository.update(ai);
		
		ActivityInst ai2=activityInstRepository.get(testvalue);
		assertEquals(newname, ai2.getName());
		
		ai.setDescription("test");
		ai.setName("value2");
		
		Map<String,Object> parameters=new HashMap<String,Object>();
		parameters.put("name", "newname");
		ai.setId(null);
		activityInstRepository.updateByWhere(ai,parameters);
		ai2=activityInstRepository.get(testvalue);
		assertEquals("value2", ai2.getName());
		assertEquals("test", ai2.getDescription());
		
		parameters.put("name", "value2");
		activityInstRepository.deleteByWhere(parameters);
		
		ai=activityInstRepository.get(testvalue);
		assertNull(ai);
	}
}
