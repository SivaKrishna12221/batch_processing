package com.siva.customs;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component("processor")
public class AddCustomItemProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		String addDirector = null;
		if (item.equalsIgnoreCase("Bahubali")) {
			return addDirector = item + " Raja mouli";
		} else if (item.equalsIgnoreCase("atharintiki daredhi")) {
			return addDirector = item + "Trivikram srinivas";
		} else if (item.equalsIgnoreCase("legend")) {
			return addDirector = item + "Boyapati sreenu";
		} else if (item.equalsIgnoreCase("srimanthudu")) {
			return addDirector = item + "koratala siva ";
		} else {
			return null;
		}
	}
}
