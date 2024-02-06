package com.aman.rest;

import com.aman.rest.user.User;
import com.aman.rest.user.UserController;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class TodoApplication implements CommandLineRunner {

	private UserController userController;

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		userController.createUser(new User("aman", "dummy"));
	}
}
