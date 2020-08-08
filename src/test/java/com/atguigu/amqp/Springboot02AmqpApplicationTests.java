package com.atguigu.amqp;

import com.atguigu.amqp.bean.Book;
import com.atguigu.amqp.config.MyAMQPConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
class Springboot02AmqpApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    //交换机，队列管理组件
    @Autowired
    AmqpAdmin amqpAdmin;


    @Test
    public void testSend(){
        rabbitTemplate.convertAndSend(MyAMQPConfig.EXCHANGE_NAME,"boot.haha",new Book("活着","余少华"));
    }

    @Test
    public void createExchange(){
        //amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
        //System.out.println("创建完成");

        //第二个参数表示是否持久化
        //amqpAdmin.declareQueue(new Queue("amqpadmin.queue",true));

        //创建绑定规则
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE,"amqpadmin.exchange","amqpadmin.haha",null));

    }

    /**
     *  1.单播（点对点）
     */
    @Test
    public void contextLoads() {
        //message需要自己构造一个；定义消息体内容和消息头
        //rabbitTemplate.send(exchange,routeKey,message);

        //object默认当成消息体，只需要传入要发送的对象，自动序列化发送给rabbitmq
        //rabbitTemplate.convertAndSend(exchange,routeKey,object);

        Map<String,Object> map = new HashMap<>();
        map.put("msg","这是第一个消息");
        map.put("data",Arrays.asList("helloworld",123,true));
        //对象被默认序列化以后发送出去
        rabbitTemplate.convertAndSend("exchange.direct","atguigu.news",new Book("西游记","吴承恩"));
    }


    @Test
    public void receive(){
        Object o = rabbitTemplate.receiveAndConvert("atguigu.news");
        System.out.println(o.getClass());
        System.out.println(o);
    }

    @Test
    public void sendMsg(){
        rabbitTemplate.convertAndSend("exchange.fanout","",new Book("三国演义","罗贯中"));

    }

}
