package com.club.business.activemq.topicbean;

import com.club.business.activemq.ActiveMqTopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

/**
 * activeMQ - 监听配置类(监听时调用此地注册的Bean,每个Bean中配置工厂方法创建监听)
 *
 * 注意：每个方法创建的对象必须set一个唯一的ClientId (此Id自定义,不能冲突,可避免不同方法调用产生阻断)
 */
@Configuration
public class TopicBean {

    @Bean(name = "topicAsnListener")
    public DefaultJmsListenerContainerFactory topicAsnListener(){
        DefaultJmsListenerContainerFactory factory = ActiveMqTopicConfig.jmsTopicListenerContainerFactory();
        factory.setClientId("vp-mid-stock-asn-98");
        return factory;
    }

    @Bean(name = "topicSoListener")
    public DefaultJmsListenerContainerFactory topicSoListener(){
        DefaultJmsListenerContainerFactory factory = ActiveMqTopicConfig.jmsTopicListenerContainerFactory();
        factory.setClientId("vp-mid-stock-so-98");
        return factory;
    }
}
