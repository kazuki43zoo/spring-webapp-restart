package com.example;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SpringWebAppRestartIT {

    @BeforeClass
    public static void setup() throws Exception {
        new Thread(MockTcpServer::main).start();
        TimeUnit.SECONDS.sleep(2);
    }

    @AfterClass
    public static void cleanup() throws Exception {
        System.in.close();
    }

    @Test
    public void test() throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        {
            String body = restTemplate.getForObject("http://localhost:8080/spring-webapp-restart/test?message=test1", String.class);
            System.out.println(body);
        }

        {
            String body = restTemplate.getForObject("http://localhost:8080/spring-webapp-restart/test?message=test2", String.class);
            System.out.println(body);
        }

        {
            Properties settings = new Properties();
            FileSystemResource settingsFile = new FileSystemResource("/opt/tomcat/env/config/settings.properties");
            if (settingsFile.exists()) {
                settings.load(settingsFile.getInputStream());
            } else {
                Files.createDirectories(settingsFile.getFile().toPath().getParent());
            }
            settings.setProperty("maxSize", String.valueOf(new Random().nextInt(10000)));
            settings.store(Files.newOutputStream(settingsFile.getFile().toPath()), "");
            restTemplate.getForObject("http://localhost:8080/spring-webapp-restart/context/restart", void.class);
        }

        {
            String body = restTemplate.getForObject("http://localhost:8080/spring-webapp-restart/test?message=test3", String.class);
            System.out.println(body);
        }

    }

}
