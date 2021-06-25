package com.club.business.activemq.product;

import javax.jms.Destination;

/**
 * activeMQ
 *
 * @Description  生产者接口
 * @author Tom
 * @date 2019-12-17
 */
public interface IProducerService {
	
	void push(Destination destination, Object message);
	
	void push(String destinationName, Object message);
}
