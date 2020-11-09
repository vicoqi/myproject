package com.vic.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 消费者
 * 需要知道 参数 tag ,keys 意思
 */
public class Consumer {

    private DefaultMQPushConsumer consumer;

    @Before
    public void producerConnBegin(){
        consumer = new DefaultMQPushConsumer("my_rocketmq");
        consumer.setNamesrvAddr("127.0.0.1:9876");

        try {
//            //订阅PushTopic下Tag为push的消息
            consumer.subscribe("broker-a", "push");

            // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
//            consumer.subscribe("broker-a", "*");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void syncSend(){
        //程序第一次启动从消息队列头取数据
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                //默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
                System.out.println("msgsList size:"+msgs.size());
                for (MessageExt msg:msgs){
                    System.out.println(msg.toString());
                    String topic = msg.getTopic();
                    System.out.println("topic = " + topic);
                    byte[] body = msg.getBody();
                    System.out.println("body:  " + new String(body));
                    String keys = msg.getKeys();
                    System.out.println("keys = " + keys);
                    String tags = msg.getTags();
                    System.out.println("tags = " + tags);
                    System.out.println("-----------------------------------------------");
                }
                System.out.println(Thread.currentThread().getName()+"   Receive New Messages handler end");
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        try {
            // 启动消费者实例
            consumer.start();
            System.out.println("Consumer Started");
            Thread.sleep(10000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


//    public static void main(String[] args) {
//        DefaultMQPushConsumer consumer =
//                new DefaultMQPushConsumer("my_rocketmq");
//        consumer.setNamesrvAddr("127.0.0.1:9876");
//        try {
//            //订阅PushTopic下Tag为push的消息
//            consumer.subscribe("broker-a", "push");
//
//            //程序第一次启动从消息队列头取数据
//            consumer.setConsumeFromWhere(
//                    ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//            consumer.registerMessageListener(new MessageListenerConcurrently() {
//                    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
//                      //默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
//                        Message msg = list.get(0);
//                        System.out.println(msg.toString());
//
//                        String topic = msg.getTopic();
//                        System.out.println("topic = " + topic);
//                        byte[] body = msg.getBody();
//                        System.out.println("body:  " + new String(body));
//                        String keys = msg.getKeys();
//                        System.out.println("keys = " + keys);
//                        String tags = msg.getTags();
//                        System.out.println("tags = " + tags);
//                        System.out.println("-----------------------------------------------");
//
//                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                    }
//                }
//            );
//            consumer.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
