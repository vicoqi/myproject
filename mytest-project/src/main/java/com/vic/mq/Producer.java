package com.vic.mq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 生产者
 */
public class Producer {

    private DefaultMQProducer producer;

    @Before
    public void producerConnBegin(){
        producer = new DefaultMQProducer("my_rocketmq");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setVipChannelEnabled(false);
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    //这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知。
    @Test
    public void syncSend(){
        try {
            Date date = new Date();    // 调用无参数构造函数
            String s = date.toString();
            System.out.println(s);

            Message msg = new Message("broker-a",
                    "push",
                    "1",
                    ("Just for test."+s).getBytes());

            SendResult result = producer.send(msg);
            System.out.println("id:" + result.getMsgId() +
                    " result:" + result.getSendStatus());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //异步消息通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间地等待Broker的响应。
    @Test
    public void asyncSend(){
        try {
            Date date = new Date();    // 调用无参数构造函数
            String s = date.toString();
            System.out.println(s);

            //开始发送
            //异步发送失败重试次数
            producer.setRetryTimesWhenSendAsyncFailed(0);

            int messageCount = 100;
            // 根据消息数量实例化倒计时计算器
            final CountDownLatch2 countDownLatch = new CountDownLatch2(messageCount);
            for (int i = 0; i < messageCount; i++) {
                final int index = i;
                // 创建消息，并指定Topic，Tag和消息体
                Message msg = new Message("TopicTest",
                        "TagA",
                        "OrderID188",
                        ("Just for test."+s).getBytes(RemotingHelper.DEFAULT_CHARSET));
                // SendCallback接收异步返回结果的回调
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.printf("%-10d OK %s %n", index,
                                sendResult.getMsgId());
                    }
                    @Override
                    public void onException(Throwable e) {
                        System.out.printf("%-10d Exception %s %n", index, e);
                        e.printStackTrace();
                    }
                });
            }
            // 等待5s
            countDownLatch.await(5, TimeUnit.SECONDS);
            // 如果不再发送消息，关闭Producer实例。
            producer.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //单向发送消息
    //这种方式主要用在不特别关心发送结果的场景，例如日志发送。
    @Test
    public void onewayProducer(){
        try {
            Date date = new Date();    // 调用无参数构造函数
            String s = date.toString();
            System.out.println(s);

            //开始发送消息
            for (int i = 0; i < 100; i++) {
                // 创建消息，并指定Topic，Tag和消息体
                Message msg = new Message("TopicTest" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " +s + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                // 发送单向消息，没有任何返回结果
                producer.sendOneway(msg);

            }
            // 如果不再发送消息，关闭Producer实例。
            producer.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @After
    public void producerConnShut(){
        producer.shutdown();
    }

}