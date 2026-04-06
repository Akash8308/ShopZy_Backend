package com.shopzy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootApplication
public class ShopzyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopzyApplication.class, args);
	}
    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        log.info("🚀 Welcome to ShopZy!!");
    }
}
