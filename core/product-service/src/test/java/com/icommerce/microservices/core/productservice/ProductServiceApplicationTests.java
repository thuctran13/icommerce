package com.icommerce.microservices.core.productservice;

import com.icommerce.microservices.core.productservice.dto.ProductInfo;
import com.icommerce.microservices.core.productservice.dto.ProductInfoList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        create("iPhone11", "2018 Apple's Product");
        create("iPhone12", "Latest Apple Product");

        entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<ProductInfoList> findAllByProductRes = restTemplate.exchange(
                createUrlWithPort("product/"),
                HttpMethod.GET,
                entity,
                ProductInfoList.class);

        assertEquals(HttpStatus.OK, findAllByProductRes.getStatusCode());
        assertNotNull(findAllByProductRes.getBody());
        assertFalse(findAllByProductRes.getBody().getProductInfoList().isEmpty());
    }

    @Test
    public void testFindById() {
        ResponseEntity<ProductInfo> createResponse = create("iPhone12", "Latest Apple Product");
        assertNotNull(createResponse.getBody());

        ResponseEntity<ProductInfo> findRes = findById(createResponse.getBody().getId());
        assertEquals(HttpStatus.OK, findRes.getStatusCode());
        assertNotNull(findRes.getBody());

        ProductInfo productInfo = findRes.getBody();
        assertEquals(createResponse.getBody().getId(), productInfo.getId());
        assertEquals("iPhone12", productInfo.getName());
        assertEquals("Latest Apple Product", productInfo.getDescription());
    }

    @Test
    public void testFindById_NOK() {
        ResponseEntity<ProductInfo> findRes = findById(-1L);
        assertEquals(HttpStatus.NOT_FOUND, findRes.getStatusCode());
    }

    @Test
    public void testCreateProduct() {
        ResponseEntity<ProductInfo> createResponse = create("iPhone12", "Latest Apple Product");
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());

        ProductInfo productInfo = createResponse.getBody();
        assertEquals("iPhone12", productInfo.getName());
        assertEquals("Latest Apple Product", productInfo.getDescription());
    }

    @Test
    public void testUpdateProduct_OK() {
        ResponseEntity<ProductInfo> createResponse = create("iPhone12", "Latest Apple Product");
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        ProductInfo productInfo = createResponse.getBody();

        // update product
        productInfo.setName("iPhone 12 Pro");
        entity = new HttpEntity<>(productInfo, httpHeaders);
        ResponseEntity<ProductInfo> updateRes = restTemplate.exchange(
                createUrlWithPort("product/" + productInfo.getId()),
                HttpMethod.PUT,
                entity,
                ProductInfo.class);

        assertEquals(HttpStatus.OK, updateRes.getStatusCode());
        productInfo = updateRes.getBody();
        assertNotNull(productInfo);
        assertEquals("iPhone 12 Pro", productInfo.getName());
    }

    @Test
    public void testUpdateProduct_NOK() {
        entity = new HttpEntity<>(new ProductInfo(), httpHeaders);
        ResponseEntity<ProductInfo> updateRes = restTemplate.exchange(
                createUrlWithPort("product/" + -1L),
                HttpMethod.PUT,
                entity,
                ProductInfo.class);

        assertEquals(HttpStatus.NOT_FOUND, updateRes.getStatusCode());
    }

    @Test
    public void testDeleteProduct_OK() {
        ResponseEntity<ProductInfo> createResponse = create("iPhone12", "Latest Apple Product");
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        ProductInfo productInfo = createResponse.getBody();

        // delete product
        entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<Void> deleteRes = restTemplate.exchange(
                createUrlWithPort("product/" + productInfo.getId()),
                HttpMethod.DELETE,
                entity,
                Void.class);
        assertEquals(HttpStatus.OK, deleteRes.getStatusCode());

        ResponseEntity<ProductInfo> findRes = findById(productInfo.getId());
        assertEquals(HttpStatus.NOT_FOUND, findRes.getStatusCode());
    }

    @Test
    public void testDeleteProduct_NOK() {
        entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<Void> deleteRes = restTemplate.exchange(
                createUrlWithPort("product/" + -1L),
                HttpMethod.DELETE,
                entity,
                Void.class);
        assertEquals(HttpStatus.NOT_FOUND, deleteRes.getStatusCode());
    }

    @Test
    public void testFindProductByKeyword() {
        create("nokia 1", "Smartphone");
        create("nokia 2", "Smartphone");
        
        String keyword = "nokia 1";
        ResponseEntity<ProductInfoList> findAllByProductRes = findByKeyword(keyword);

        assertEquals(HttpStatus.OK, findAllByProductRes.getStatusCode());
        assertNotNull(findAllByProductRes.getBody());
        assertEquals(1, findAllByProductRes.getBody().getProductInfoList().size());

        keyword = "smartphone";
        findAllByProductRes = findByKeyword(keyword);
        assertEquals(HttpStatus.OK, findAllByProductRes.getStatusCode());
        assertNotNull(findAllByProductRes.getBody());
        assertEquals(2, findAllByProductRes.getBody().getProductInfoList().size());
    }

    @Test
    public void testFindProductByKeyword_NotFound() {
        String keyword = "abcxzy";
        ResponseEntity<ProductInfoList> findAllByProductRes = findByKeyword(keyword);

        assertEquals(HttpStatus.OK, findAllByProductRes.getStatusCode());
        assertNotNull(findAllByProductRes.getBody());
        assertTrue(findAllByProductRes.getBody().getProductInfoList().isEmpty());
    }

    private ResponseEntity<ProductInfo> create(String name, String description) {
        ProductInfo productCreationRes = new ProductInfo();
        productCreationRes.setName(name);
        productCreationRes.setDescription(description);
        productCreationRes.setPrice(BigDecimal.valueOf(1000));
        productCreationRes.setWeight(400d);
        productCreationRes.setState("CREATED");
        productCreationRes.setStatus("INACTIVE");

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

    private ResponseEntity<ProductInfoList> findByKeyword(String keyword) {
        entity = new HttpEntity<>(null, httpHeaders);
        return restTemplate.exchange(
                createUrlWithPort("product/find?keyword=" + keyword),
                HttpMethod.GET,
                entity,
                ProductInfoList.class);
    }

    private String createUrlWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
