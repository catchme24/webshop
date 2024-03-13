package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.GsonBuilderUtils;

@SpringBootApplication
public class ExampleApplication {

	public static void main(String[] args) {
		System.out.println("НОВЫЙ КОММИТ");
		System.out.println("еще строчка");


		System.out.println("Еще строчечка");
		SpringApplication.run(ExampleApplication.class, args);
	}


}
