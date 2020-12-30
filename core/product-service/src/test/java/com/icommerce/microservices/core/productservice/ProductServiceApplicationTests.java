package com.icommerce.microservices.core.productservice;

import com.icommerce.microservices.core.productservice.dto.ProductInfo;
import com.icommerce.microservices.core.productservice.dto.ProductResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {
	
	@LocalServerPort
	private int port;
	
	private TestRestTemplate restTemplate = new TestRestTemplate();
	private static HttpHeaders httpHeaders = new HttpHeaders();
	
	@BeforeAll
	public static void before() {
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	}

	@Test
	public void testGetAllProduct() {
		HttpEntity entity; 
		
		//create new product
		ProductInfo productCreationRes = new ProductInfo();
		productCreationRes.setName("iPhone12");
		productCreationRes.setDescription("Latest Apple Product");
		productCreationRes.setPrice(BigDecimal.valueOf(1000));
		productCreationRes.setWeight(400d);
		entity = new HttpEntity<>(productCreationRes, httpHeaders);
		ResponseEntity<ProductInfo> productRes = restTemplate.exchange(
				createUrlWithPort("product/"),
				HttpMethod.POST,
				entity,
				ProductInfo.class);
		
		// find product by id
		entity = new HttpEntity<>(null, httpHeaders);
		productRes = restTemplate.exchange(
				createUrlWithPort("product/" + productRes.getBody().getId()),
				HttpMethod.GET,
				entity,
				ProductInfo.class);

		// update product
		productCreationRes.setName("iPhone 12 Pro");
		entity = new HttpEntity<>(productCreationRes, httpHeaders);
		productRes = restTemplate.exchange(
				createUrlWithPort("product/" + productRes.getBody().getId()),
				HttpMethod.PUT,
				entity,
				ProductInfo.class);

		// delete
		entity = new HttpEntity<>(null, httpHeaders);
		ResponseEntity<Void> delete = restTemplate.exchange(
				createUrlWithPort("product/" + productRes.getBody().getId()),
				HttpMethod.DELETE,
				entity,
				Void.class);

		// find all product
		entity = new HttpEntity<>(null, httpHeaders);
		ResponseEntity<ProductResponse> findAllRes = restTemplate.exchange(
				createUrlWithPort("product/"),
				HttpMethod.GET,
				entity,
				ProductResponse.class);
		
		assertTrue(findAllRes.getBody() != null && findAllRes.getBody().getProductInfoList() != null);
		assertTrue(findAllRes.getBody().getProductInfoList().isEmpty());
	}

	private String createUrlWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
