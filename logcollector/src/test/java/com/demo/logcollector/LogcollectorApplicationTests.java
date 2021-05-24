package com.demo.logcollector;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.logcollector.service.EventService;

@SpringBootTest(args = "src/test/resources/test")
class LogcollectorApplicationTests {

	@Autowired
	EventService eventService;
	
	@Test
	public void contextLoad() {}


	@Test
	public void processEvents() throws Exception {
		assertThat(eventService.getAllEvents().size()).isEqualTo(3);
	}

}
