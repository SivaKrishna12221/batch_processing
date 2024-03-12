package com.siva.customs;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component("reader")
public class StringCustomsItemReader implements ItemReader<String> {

	int count = 0;

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		String movie[] = new String[] { "Bahubali", "kgf", "atharintiki daredhi", "legend", "srimanthudu" };

		if (count < movie.length) {
			return movie[count++];
		}
		return null;
	}
}
