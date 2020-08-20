package com.handset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = {"com.handset.repository"})
public class HandsetServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HandsetServiceApplication.class, args);
	}

}
