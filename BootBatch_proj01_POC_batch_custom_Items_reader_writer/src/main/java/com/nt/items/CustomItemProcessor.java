package com.nt.items;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
@Component("cip")
public class CustomItemProcessor implements ItemProcessor<String, String> {

	public CustomItemProcessor() {
		System.out.println("CustomItemProcessor.CustomItemProcessor()");

	}
	@Override
	public String process(String item) throws Exception {
		String updatedString = null;
		if (item.equalsIgnoreCase("Head First Java")) {
			updatedString = item + " is written by Kathy Sierra";
		} else if (item.equalsIgnoreCase("Begining programming with Java")) {
			updatedString = item + " is written by Dummies";
		} else if (item
				.equalsIgnoreCase("Programming basics for absolute beginner")) {
			updatedString = item + "written by Nathon Clark";
		} else if (item.equalsIgnoreCase("A begginer guide")) {
			updatedString = item + "written by Herbad Schildit";
		} else if (item.equalsIgnoreCase("Think Java")) {
			updatedString = item
					+ " written by Allen Downey and Chris Mayfield";
		}

		return updatedString;
	}
}
