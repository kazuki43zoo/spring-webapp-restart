package com.example.management;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/context")
@RestController
public class ApplicationContextController {

    private final ConfigurableApplicationContext context;

    public ApplicationContextController(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @GetMapping("restart")
    public void restart() {
        System.out.println("Restarting root context...");
        Optional.ofNullable(context.getParent())
                .map(ConfigurableApplicationContext.class::cast)
                .ifPresent(ConfigurableApplicationContext::refresh);
        System.out.println("Restarted root context.");
        System.out.println("Restarting servlet context...");
        context.refresh();
        System.out.println("Restarted servlet context.");
    }

}
