package com.datamaster.polls;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.datamaster.polls"})
public class PollsApplication {
    public static void main(String[] args) {
        run(PollsApplication.class, args);
    }
}

