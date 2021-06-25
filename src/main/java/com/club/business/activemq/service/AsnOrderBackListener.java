package com.club.business.activemq.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * demo 监听activeMQ接收消息 测试类
 *
 * @author Tom
 * @date 2019-12-17
 */
@Service
public class AsnOrderBackListener {

    private static final Logger log = LoggerFactory.getLogger(AsnOrderBackListener.class);

    /**
     * 监听消息方法demo
     *
     * 注意：@JmsListener(destination = "asnOrderBackTopic", containerFactory = "topicAsnListener")中
     *      destination参数为destination字段,根据消息发送者定义的名称书写
     *      containerFactory参数为 监听配置类TopicBean中配置的名称,只有正确配置才能监听
     * @param message 监听到消息字符串
     */
//    @JmsListener(destination = "asnOrderBackTopic", containerFactory = "topicAsnListener")
    public void getMessage(String message) {
        log.info("入库订单监听消息队列，传来的值为:{}",message);
        try {
            if (StringUtils.isNotBlank(message)) {
                JSONObject list = JSONObject.parseObject(message);
                if (list == null) {
                    return;
                }
                String msg = list.getString("msg");
                System.err.println("接收到消息啦,消息内容为：" + msg);
                /**处理逻辑内容*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}




	



