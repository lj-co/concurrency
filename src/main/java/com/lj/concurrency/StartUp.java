package com.lj.concurrency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class StartUp {

	public static void main(String[] args) {
		SpringApplication.run(StartUp.class, args);
		log.info("并发编程（concurrency）启动成功");
	}
}
