package com.club.business.activemq;

import com.club.business.config.ActiveMqConfig;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import javax.jms.ConnectionFactory;
import javax.jms.Session;

/**
 * ActiveMQ 配置类
 */
@Configuration
public class ActiveMqTopicConfig {

    /**
     * JMS 队列的监听容器工厂
     */
    public static DefaultJmsListenerContainerFactory jmsTopicListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setPubSubDomain(true);
        factory.setSessionTransacted(true);
        factory.setAutoStartup(true);
        /**开启持久化订阅*/
        factory.setSubscriptionDurable(true);
        /**重连间隔时间*/
        factory.setRecoveryInterval(1000L);
        factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        return factory;
    }

    /**
     * 链接工厂方法
     * @return
     */
    public static ConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(ActiveMqConfig.ACTIVEMQ_URL);
        connectionFactory.setUserName(ActiveMqConfig.ACTIVEMQ_USER);
        connectionFactory.setPassword(ActiveMqConfig.ACTIVEMQ_PASSWORD);
        /**设置重发属性*/
        connectionFactory.setRedeliveryPolicy(redeliveryPolicy());
        return connectionFactory;
    }

    /**
     * 设置重发属性
     * @return
     */
    public static RedeliveryPolicy redeliveryPolicy(){
        RedeliveryPolicy redeliveryPolicy=   new RedeliveryPolicy();
        /**是否在每次尝试重新发送失败后,增长这个等待时间*/
        redeliveryPolicy.setUseExponentialBackOff(true);
        /**重发次数,默认为6次,这里设置为10次,-1表示不限次数*/
        redeliveryPolicy.setMaximumRedeliveries(0);
        /**重发时间间隔,默认为1毫秒,设置为10000毫秒*/
        redeliveryPolicy.setInitialRedeliveryDelay(10000);
        /**
         * 表示没有拖延只有UseExponentialBackOff(true)为true时生效
         * 第一次失败后重新发送之前等待10000毫秒,第二次失败再等待10000 * 2毫秒
         * 第三次翻倍10000 * 2 * 2，以此类推
         */
        redeliveryPolicy.setBackOffMultiplier(2);
        /**是否避免消息碰撞*/
        redeliveryPolicy.setUseCollisionAvoidance(true);
        /**设置重发最大拖延时间360000毫秒 表示没有拖延只有UseExponentialBackOff(true)为true时生效*/
        redeliveryPolicy.setMaximumRedeliveryDelay(360000);
        return redeliveryPolicy;
    }
}