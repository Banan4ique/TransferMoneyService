package ru.netology.TransferMoney;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.TransferMoney.DTO.Confirmation;

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

		ResponseEntity<String> entityFromBack = restTemplate.postForEntity(
				"http://localhost:" + backPort + "/confirmOperation",
				new HttpEntity<Confirmation>(new Confirmation("0", "0000")),
				String.class);

		Assertions.assertTrue(frontApp.isRunning(), "front app is not running");
		Assertions.assertEquals(200, entityFromBack.getStatusCode().value());
		System.out.println("Response from back: " + entityFromBack.getBody());
	}
}
