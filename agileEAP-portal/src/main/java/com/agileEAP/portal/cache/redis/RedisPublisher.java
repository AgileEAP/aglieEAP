package com.agileEAP.portal.cache.redis;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.scheduling.annotation.Scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agileEAP.portal.jms.TradeRecord;


public class RedisPublisher {	
	private static Logger logger = LoggerFactory.getLogger(RedisPublisher.class);
    private final RedisTemplate< String, Object > redisTemplate;
    private final ChannelTopic topic; 
    private final AtomicLong counter = new AtomicLong( 0 );

    public RedisPublisher( final RedisTemplate< String, Object > redisTemplate, 
            final ChannelTopic topic ) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    @Scheduled( fixedDelay = 100 )
    public void publish() {
    	
    	logger.info("publish Message "+topic.getTopic()+counter.get()+", " + Thread.currentThread().getName());
    	redisTemplate.convertAndSend( topic.getTopic(), "Message " + counter.incrementAndGet() + 
            ", " + Thread.currentThread().getName() );
    	

		TradeRecord tradeRecord = new TradeRecord();
		tradeRecord.setTranCode("trade"+UUID.randomUUID());
		tradeRecord.setGoodsCode("goodsCode"+UUID.randomUUID());
		
		redisTemplate.convertAndSend( topic.getTopic(),tradeRecord);
		
 }
}