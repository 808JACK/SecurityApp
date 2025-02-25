package com.example.SecurityApp;

import com.example.SecurityApp.entities.User;
import com.example.SecurityApp.services.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityAppApplicationTests {

	@Autowired
	private JWTService jwtService;

	@Test
	void contextLoads() {
		// Ensure the user ID is properly set
		User user = new User(44L, "sarthak@gmail.com", "1234");  // Use the constructor correctly

		// Now generate the token
		String token = jwtService.generateToken(user);

		System.out.println(token);

		Long id = jwtService.getUserIdFromToken(token);

		System.out.println(id);
	}
}
