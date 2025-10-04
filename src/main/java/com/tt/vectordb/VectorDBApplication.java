package com.tt.vectordb;

import com.tt.vectordb.config.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VectorDBApplication {

	public static void main(String[] args) {
		try {
			Configuration.getConfiguration();
			SpringApplication.run(VectorDBApplication.class, args);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
	}

}
