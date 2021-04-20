package com.aynu.project.highconcurrentproject.service.impl;


import com.aynu.project.highconcurrentproject.bean.Message;
import com.aynu.project.highconcurrentproject.service.ActiveMqUserSerive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author admin
 */
@Service
public class  ActiveMqUserServiceImpl implements ActiveMqUserSerive {

    //注入spring自动生产的jmstemplate
    @Autowired
    JmsTemplate jmsTemplate;

    //自定义地址
    private static final String myDestination = "my-destination";

    @Override
    public void sendUser(Message message) {
        System.out.println("发送了消息"+message.getUsername());
        jmsTemplate.convertAndSend(myDestination,message);
    }

    @JmsListener(destination = myDestination)
    @Override
    public void receiveUser(Message message) {
        System.out.println("消息的内容是"+message.getNote());
    }
}
