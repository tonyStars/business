package com.club.business.activemq.demo;

import com.alibaba.fastjson.JSONObject;
import com.club.business.activemq.product.IProducerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.jms.Destination;

/**
 * 发送消息测试类
 */
@Controller
@RequestMapping("/SendMqTest")
public class SendMqTest {

    @Autowired
    private IProducerService producerService;

    /**
     * 消息发送方法
     * @param msg 发送内容
     */
    @RequestMapping(value = "/msg")
    public void sendMq(String msg){
        JSONObject json = new JSONObject();
        json.put("msg",msg);
        try {
            /**作为生产者 asnOrderBackTopic 为消息名称唯一标识,消费者根据此名接收消息*/
            Destination destination = new ActiveMQQueue("asnOrderBackTopic");
            producerService.push(destination, json.toString());
        } catch (Exception e) {
            /**
             * 此处可将异常数据插入数据库或记录
             */
            e.printStackTrace();
        }
    }

}
