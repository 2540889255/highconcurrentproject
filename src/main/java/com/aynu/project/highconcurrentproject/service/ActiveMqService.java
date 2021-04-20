package com.aynu.project.highconcurrentproject.service;

/**
 * @author admin
 */
public interface ActiveMqService {

    /**
     * 发送消息
     */
    public void sentMessage(String message);

    /**
     * 接收消息
     */
    public void receiveMessage(String message);
}
