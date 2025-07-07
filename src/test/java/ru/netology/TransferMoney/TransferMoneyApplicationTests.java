package ru.netology.TransferMoney;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransferMoneyApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Container
	private final GenericContainer<?> frontApp = new GenericContainer<>(
			"transfer-master-front:latest").withExposedPorts(3000);

	@Container
	private final GenericContainer<?> backApp = new GenericContainer<>("apptm:latest")
			.withExposedPorts(8080);

	@Test
	void contextLoads() {
		Integer backPort = backApp.getMappedPort(8080);

		ResponseEntity<String> entityFromBack = restTemplate.getForEntity(
				"http://localhost:" + backPort, String.class);

		Assertions.assertTrue(frontApp.isRunning(), "front app is not running");
		Assertions.assertEquals(200, entityFromBack.getStatusCode().value());
		System.out.println("Response from back: " + entityFromBack.getBody());
	}
}
