package com.club.business.activemq.product.impl;

import com.club.business.activemq.product.IProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * activeMQ
 *
 * @Description 生产者实现类
 * @author Tom
 * @date 2019-12-17
 */
@Service
public class ProducerServiceImpl implements IProducerService {
	
	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public void push(Destination destination, Object message) {
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message.toString());
			}
		});
	}

	@Override
	public void push(String destinationName, Object message) {
		jmsTemplate.send(destinationName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message.toString());
			}
		});
	}
}
