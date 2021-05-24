package com.demo.logcollector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.demo.logcollector.service.EventService;

@SpringBootApplication
public class LogcollectorApplication implements CommandLineRunner {

	@Autowired
	private EventService eventService;

	public static void main(String[] args) {
		SpringApplication.run(LogcollectorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args == null || args.length != 1) {
			throw new IllegalArgumentException("Given arguments is not valid. There has to be 1 argument.");
		}

		String filePath = args[0];
		eventService.parseEvents(filePath);

	}

}
