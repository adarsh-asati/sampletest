package com.demo.logcollector.util;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utility {

	private static final Logger LOG = LoggerFactory.getLogger(Utility.class);

	/**
	 * @param <T>
	 * @param string
	 * @param pojo
	 * @return
	 */
	public static <T> T convert(String string, Class<T> pojo) {
		LOG.debug("Converting line {}", string);
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(string, pojo);
		} catch (IOException e) {
			LOG.error("Error occred while converting the line. {}", e);
			throw new UncheckedIOException(e);
		}
	}
}
