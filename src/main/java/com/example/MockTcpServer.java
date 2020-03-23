package com.example;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

public class MockTcpServer {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String... args) {
        try (ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/mockTcpServerContext.xml")) {
            context.registerShutdownHook();
            while (true) {
                System.out.print("> ");
                String command = scanner.nextLine();
                if ("q".equalsIgnoreCase(command)) {
                    System.out.println("Quit application!");
                    break;
                } else {
                    System.out.println("Invalid command. Please input following commands.");
                    System.out.println("--------------------");
                    System.out.println("q : Quit application");
                    System.out.println("--------------------");
                    System.out.print("> ");
                }
            }
        } catch (IllegalStateException | java.util.NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

}
