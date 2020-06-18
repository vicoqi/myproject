package com.vic.mq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
/**
 * 生产者
 */
public class Producer {

    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("my_rocketmq");
        producer.setNamesrvAddr("127.0.0.1:9876");
        try {
            producer.start();

            Message msg = new Message("broker-a",
                    "push",
                    "1",
                    "Just for test.".getBytes());

            SendResult result = producer.send(msg);
            System.out.println("id:" + result.getMsgId() +
                    " result:" + result.getSendStatus());

            msg = new Message("broker-a",
                    "push",
                    "2",
                    "Just for test.".getBytes());

            result = producer.send(msg);
            System.out.println("id:" + result.getMsgId() +
                    " result:" + result.getSendStatus());

//            msg = new Message("PushTopic",
//                    "push",
//                    "1",
//                    "Just for test.".getBytes());
//
//            result = producer.send(msg);
//            System.out.println("id:" + result.getMsgId() +
//                    " result:" + result.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            producer.shutdown();
        }
    }
}