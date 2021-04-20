package com.aynu.project.highconcurrentproject.service;


import com.aynu.project.highconcurrentproject.bean.Message;

/**
 * @author admin
 */
public interface ActiveMqUserSerive {

    /**
     * 发送消息
     * @param message
     */
    public void sendUser(Message message);

    /**
     * 接收消息
     * @param message
     */
    public void receiveUser(Message message);
}
