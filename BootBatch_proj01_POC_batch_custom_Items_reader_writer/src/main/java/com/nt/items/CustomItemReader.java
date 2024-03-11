package com.nt.items;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component("cir")
public class CustomItemReader implements ItemReader<String> {
	int count = 0;

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		String[] books = new String[] { "Head First Java", "Begining programming with Java",
				"Programming basics for absolute beginner", "A begginer guide", "Think Javas" };

		if (count < books.length) {
			return books[count++];
		} else {
			return null;
		}
	}
}
