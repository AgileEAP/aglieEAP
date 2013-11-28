package com.agileEAP.test.mongodb;

import static org.junit.Assert.*;

import java.util.UUID;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.agileEAP.portal.mongodb.*;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


@DirtiesContext
@ActiveProfiles("test")
@ContextConfiguration(locations = { "/applicationContext.xml", "/mongodb/applicationContext-mongodb.xml" })
public class ProcessDefineRepositoryTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private ProcessDefineRepository processDefineRepository;

	@Test
	public void sampleTest() {

		ProcessDefine value=new ProcessDefine();
		value.setId(UUID.randomUUID().toString());
		value.setName("apply");		
		value.setContent("test");
		
		processDefineRepository.save(value);
		
		ProcessDefine result=processDefineRepository.findOne(value.getId());
		
		assertEquals(value.getId(), result.getId());
		
		List<ProcessDefine> result2=processDefineRepository.searchByName("a");
		assertNotNull(result2);
		
		ProcessDefine value2=new ProcessDefine();
		value2.setId(UUID.randomUUID().toString());
		value2.setName("apply2");		
		value2.setContent("test2");
		processDefineRepository.save(value2);
		
		ProcessDefine value3=new ProcessDefine();
		value3.setId(UUID.randomUUID().toString());
		value3.setName("aply3");		
		value3.setContent("test3");
		processDefineRepository.save(value3);
		
		List<ProcessDefine>  result3=processDefineRepository.searchByNameLike("app");
		assertEquals(result3.size(),2);
		
		List<ProcessDefine>  result4=processDefineRepository.searchByNameLike("ap");
		assertEquals(result4.size(),3);
		
		processDefineRepository.delete(value.getId());
		processDefineRepository.delete(value2.getId());
		processDefineRepository.delete(value3.getId());
		result=processDefineRepository.findOne(value.getId());
		assertNull(result);
		
	}	
}
