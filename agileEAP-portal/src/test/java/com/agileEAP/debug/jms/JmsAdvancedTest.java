package com.agileEAP.debug.jms;

import static org.junit.Assert.*;

import java.util.UUID;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.agileEAP.portal.jms.NotifyMessageListener;
import com.agileEAP.portal.jms.NotifyMessageProducer;
import com.agileEAP.portal.jms.TradeRecord;
import com.agileEAP.utils.Threads;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


@DirtiesContext
@ActiveProfiles("test")
@ContextConfiguration(locations = { "/applicationContext.xml", "/jms/applicationContext-jms.xml" })
public class JmsAdvancedTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private NotifyMessageProducer notifyMessageProducer;

	@Resource
	private JmsTemplate advancedJmsTemplate;

	@Resource
	private Destination advancedNotifyTopic;

	@Test
	public void queueMessage() {
		Threads.sleep(1000);

		TradeRecord tradeRecord = new TradeRecord();
		tradeRecord.setTranCode("trade"+UUID.randomUUID());
		tradeRecord.setGoodsCode("goodsCode"+UUID.randomUUID());

		notifyMessageProducer.sendQueue(tradeRecord);
		Threads.sleep(1000);
	}

	@Test
	public void topicMessage() {
		Threads.sleep(1000);

		TradeRecord tradeRecord = new TradeRecord();
		tradeRecord.setTranCode("trade"+UUID.randomUUID());
		tradeRecord.setGoodsCode("goodsCode"+UUID.randomUUID());

		notifyMessageProducer.sendTopic(tradeRecord);
		Threads.sleep(1000);
	}

	@Test
	public void topicMessageWithWrongType() {
		Threads.sleep(1000);

		advancedJmsTemplate.send(advancedNotifyTopic, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {

				MapMessage message = session.createMapMessage();
				message.setStringProperty("objectType", "role");
				return message;
			}
		});

		Threads.sleep(1000);
	}
}
