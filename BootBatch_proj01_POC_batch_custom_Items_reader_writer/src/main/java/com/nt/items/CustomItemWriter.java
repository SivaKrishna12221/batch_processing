package com.nt.items;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("ciw")
public class CustomItemWriter implements ItemWriter<String> {

	public CustomItemWriter() {
		// TODO Auto-generated constructor stub
		System.out.println("CustomItemWriter.:no arg constructor");
	}

	/*@Override
	public void write(Chunk<? extends String> chunk) throws Exception {
		System.out.println("CustomItemWriter.write()");
		chunk.forEach(System.out::println);
	
	}*/
	@Override
	public void write(List<? extends String> items) throws Exception {
		// TODO Auto-generated method stub
		items.forEach(System.out::println);
	}
}
