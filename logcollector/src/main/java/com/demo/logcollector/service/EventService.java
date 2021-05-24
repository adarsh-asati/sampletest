package com.demo.logcollector.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.logcollector.model.Event;
import com.demo.logcollector.model.LogStatement;
import com.demo.logcollector.repository.EventRepository;
import com.demo.logcollector.repository.LogRepository;
import com.demo.logcollector.util.Utility;

@Service
public class EventService {
	private static final Logger LOG = LoggerFactory.getLogger(EventService.class);

	private static final int ALERT_DURATION = 4;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private LogRepository logRepository;

	public Event addEvent(Event event) {
		LOG.info("Adding event {} ", event);
		return eventRepository.save(event);
	}

	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}

	/**
	 * @param filePath
	 * 
	 *                 Method used to parse logs and create events
	 * 
	 * @throws IOException
	 */
	public void parseEvents(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		Files.lines(path).parallel()// stream over each line
				.filter(line -> !line.isEmpty()) // skipping empty lines
				.map(line -> Utility.convert(line, LogStatement.class)).parallel()// converting line to log statement
				.forEach(logStatement -> processLogAndCreateEvent(logStatement));// process each log statement and
																					// create event
		;

	}

	/**
	 * @param logStatement
	 * 
	 *                     checks if log statement is present in repository
	 * 
	 *                     if it is present, create a event for the same. if absent
	 *                     saves the log statement to the repository.
	 * 
	 */
	public void processLogAndCreateEvent(LogStatement logStatement) {
		LOG.info("Processing Log statement {}", logStatement);
		Optional<LogStatement> log = logRepository.findById(logStatement.getId());// using database We can also opt for
																					// concurrent hashmap. It can create
																					// problems for large files and can
																					// eat-up memory.
		if (log.isPresent()) {// if log is present calculate the duration and create event.
			long duration = Math.abs(log.get().getTimestamp() - logStatement.getTimestamp());
			createEvent(logStatement, duration);
		} else {
			logRepository.save(logStatement);
		}

	}

	/**
	 * @param logStatement
	 * @param duration
	 *                     <p>
	 *                     create a event for the given logstatement and
	 *                     duration<br>
	 * 
	 *                     {@link Event#isAlert()} is true if duration >
	 *                     {@link #ALERT_DURATION}
	 *                     </p>
	 */
	private void createEvent(LogStatement logStatement, long duration) {
		Event event = new Event();
		event.setId(logStatement.getId());
		event.setDuration(duration);
		event.setType(logStatement.getType());
		event.setHost(logStatement.getHost());
		event.setAlert((duration > ALERT_DURATION) ? true : false);
		addEvent(event);
	}
}
