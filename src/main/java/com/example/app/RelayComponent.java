package com.example.app;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

public class RelayComponent implements InitializingBean, DisposableBean {

    private String id;

    @Value("${maxSize:10}")
    private int maxSize;

    public Message<?> execute(Message<?> message) {
        return MessageBuilder.withPayload(message.getPayload() + " -> " + id + "(maxSize=" + maxSize + ")").build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        id = UUID.randomUUID().toString();
        System.out.println(getClass() + ".afterPropertiesSet " + id);

    }

    @Override
    public void destroy() throws Exception {
        System.out.println(getClass() + ".destroy " + id);
    }

}
