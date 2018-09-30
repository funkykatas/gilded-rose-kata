package com.gildedrose;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GildedRoseApplication {

    public static void main(String[] args) {
        SpringApplication.run(GildedRoseApplication.class, args);
    }

}
