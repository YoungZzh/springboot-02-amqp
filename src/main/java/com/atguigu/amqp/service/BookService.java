package com.atguigu.amqp.service;

import com.atguigu.amqp.bean.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Author:Young
 * Date:2020/7/18-22:10
 */
@Service
public class BookService {

    @RabbitListener(queues = "atguigu")
    public void receiveBook(Book book){
        System.out.println("收到消息：" + book);
    }

    @RabbitListener(queues = "atguigu.news")
    public void receiveBook02(Message message){
        System.out.println(message.getBody());
        System.out.println(message.getMessageProperties());
        System.out.println(message.getClass());
    }
}
