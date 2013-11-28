package com.agileEAP.test.cache.redis;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.agileEAP.portal.cache.redis.RedisPublisher;
import com.agileEAP.portal.jms.TradeRecord;
import com.agileEAP.utils.Threads;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


@DirtiesContext
@ActiveProfiles("test")
@ContextConfiguration(locations = { "/applicationContext.xml", "/cache/applicationContext-redis.xml" })
public class RedisMQTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private RedisPublisher redisPublisher;

	@Test
	public void queueMessage() {
		Threads.sleep(1000);

		try
		{
			redisPublisher.publish();
		}
		catch(Exception ex)
		{
			System.out.print(ex.getMessage());
		}
		Threads.sleep(1000);
	}	
}
