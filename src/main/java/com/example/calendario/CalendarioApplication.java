package com.example.calendario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CalendarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalendarioApplication.class, args);
    }

}
