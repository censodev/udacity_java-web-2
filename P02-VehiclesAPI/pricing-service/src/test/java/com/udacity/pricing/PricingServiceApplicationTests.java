package com.udacity.pricing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.pricing.domain.price.Price;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PricingServiceApplicationTests {
	@LocalServerPort
	private int port;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void contextLoads() throws URISyntaxException, IOException, InterruptedException {
		var vehicleId = 5;
		var uri = new URI("http://localhost:" + port + "/services/price?vehicleId=" + vehicleId);
		var req = HttpRequest.newBuilder(uri).GET().build();
		var res = HttpClient.newHttpClient().send(req, HttpResponse.BodyHandlers.ofString());
		var price = objectMapper.readValue(res.body(), Price.class);
		assertNotNull(price);
		assertNotNull(price.getPrice());
		assertNotNull(price.getCurrency());
		assertNotNull(price.getVehicleId());
	}

}
