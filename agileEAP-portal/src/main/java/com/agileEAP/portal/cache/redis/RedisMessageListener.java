package com.agileEAP.portal.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class RedisMessageListener implements MessageListener {
	private static Logger logger = LoggerFactory.getLogger(RedisMessageListener.class);
	
    @Override
    public void onMessage( final Message message, final byte[] pattern ) {  
    	
    	logger.info("Message received: " + message.toString());        
        System.out.println("Message received: " + message.toString() );
    }
}