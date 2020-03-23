package com.example.app;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class InboundController implements InitializingBean, DisposableBean {

    @Autowired
    InboundGateway gateway;

    @GetMapping
    public String test(String message) {
        return gateway.execute(message);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(getClass() + ":afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(getClass() + ":destroy");
    }
}
