package com.icommerce.microservices.core.productservice;

import com.icommerce.microservices.core.productservice.dto.ProductInfo;
import com.icommerce.microservices.core.productservice.dto.ProductResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {
	
	@LocalServerPort
	private int port;
	
	private TestRestTemplate restTemplate = new TestRestTemplate();
	private static HttpHeaders httpHeaders = new HttpHeaders();
	private HttpEntity entity = null;
	
	@BeforeAll
	public static void before() {
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	}
	
	@Test
	public void testFindAllProducts() {
		
	}

	@Test
	public void testFindById() {

	}

	@Test
	public void testCreateProduct() {
		
	}

	@Test
	public void testUpdateProduct_OK() {
		
	}

	@Test
	public void testUpdateProduct_NOK() {

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

	private ResponseEntity<ProductInfo> create(String title, String description, Long product_id) {
		ProductInfo productCreationRes = new ProductInfo();
		productCreationRes.setName("iPhone12");
		productCreationRes.setDescription("Latest Apple Product");
		productCreationRes.setPrice(BigDecimal.valueOf(1000));
		productCreationRes.setWeight(400d);

		entity = new HttpEntity<>(productCreationRes, httpHeaders);
		return restTemplate.exchange(
				createUrlWithPort("product/"),
				HttpMethod.POST,
				entity,
				ProductInfo.class);
	}

	private ResponseEntity<ProductInfo> findById(Long id) {
		entity = new HttpEntity<>(null, httpHeaders);
		return restTemplate.exchange(
				createUrlWithPort("product/" + id),
				HttpMethod.GET,
				entity,
				ProductInfo.class);
	}

	private String createUrlWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
