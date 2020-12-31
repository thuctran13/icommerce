package com.icommerce.microservices.core.reviewservice;

import com.icommerce.microservices.core.reviewservice.dto.ReviewInfo;
import com.icommerce.microservices.core.reviewservice.dto.ReviewResponse;
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

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewServiceApplicationTests {

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
    public void testFindAllReviews() {
        create("Good product", "This product is best product ever, bla bla", 1L);
        create("Product OK", "This product is best product ever, bla bla", 1L);

        entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<ReviewResponse> findAllRes = restTemplate.exchange(
                createUrlWithPort("review/"),
                HttpMethod.GET,
                entity,
                ReviewResponse.class);

        assertNotNull(findAllRes.getBody());
        assertFalse(findAllRes.getBody().getReviewInfoList().isEmpty());
    }

    @Test
    public void testFindAllByProduct() {
        Long productId = 3L;
        create("Good product", "This product is best product ever, bla bla", productId);
        create("Product OK", "This product is best product ever, bla bla", productId);
        create("So so", "This product is best product ever, bla bla", productId);

        entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<ReviewResponse> findAllByProductRes = restTemplate.exchange(
                createUrlWithPort("review/byProductId/" + productId),
                HttpMethod.GET,
                entity,
                ReviewResponse.class);

        assertNotNull(findAllByProductRes.getBody());
        assertEquals(3, findAllByProductRes.getBody().getReviewInfoList().size());
    }

    @Test
    public void testFindById() {
        ReviewInfo reviewInfo = create("Good product", "This product is best product ever, bla bla", 1L);

        ResponseEntity<ReviewInfo> response = findById(reviewInfo.getId());

        assertEquals(reviewInfo.getId(), response.getBody().getId());
    }

    @Test
    public void testCreateReview() {
        ReviewInfo reviewInfo = create("Good product", "This product is best product ever, bla bla", 1L);

        assertNotNull(reviewInfo);
        assertEquals("Good product", reviewInfo.getTitle());
        assertEquals("This product is best product ever, bla bla", reviewInfo.getDescription());
    }

    @Test
    public void testUpdateReview_OK() {
        //create review
        ReviewInfo reviewInfo = create("Good product", "This product is best product ever, bla bla", 1L);

        //update review
        reviewInfo.setDescription("Ok product");
        entity = new HttpEntity<>(reviewInfo, httpHeaders);
        ResponseEntity<ReviewInfo> reviewRes = restTemplate.exchange(
                createUrlWithPort("review/" + reviewInfo.getId()),
                HttpMethod.PUT,
                entity,
                ReviewInfo.class);
        assertEquals(HttpStatus.OK, reviewRes.getStatusCode());
        assertTrue(reviewRes.getBody() != null && reviewRes.getBody().getTitle() != null);
        assertEquals(reviewInfo.getTitle(), reviewRes.getBody().getTitle());
    }

    @Test
    public void testUpdateReview_NOK() {
        ReviewInfo reviewInfo = create("Good product", "This product is best product ever, bla bla", 1L);

        //update review with dummy id
        reviewInfo.setDescription("Ok product");
        entity = new HttpEntity<>(reviewInfo, httpHeaders);

        ResponseEntity<ReviewInfo> reviewRes = restTemplate.exchange(
                createUrlWithPort("review/" + -1L),
                HttpMethod.PUT,
                entity,
                ReviewInfo.class);
        assertEquals(HttpStatus.NOT_FOUND, reviewRes.getStatusCode());
    }

    @Test
    public void testDeleteReview_OK() {
        //create review
        ReviewInfo reviewInfo = create("Good product", "This product is best product ever, bla bla", 2L);

        //delete review
        entity = new HttpEntity<>(null, httpHeaders);
        restTemplate.exchange(
                createUrlWithPort("review/" + reviewInfo.getId()),
                HttpMethod.DELETE,
                entity,
                Void.class);

        //find by id & verify
        ResponseEntity<ReviewInfo> response = findById(reviewInfo.getId());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private ReviewInfo create(String title, String description, Long product_id) {
        ReviewInfo reviewInfo = new ReviewInfo();
        reviewInfo.setTitle(title);
        reviewInfo.setDescription(description);
        reviewInfo.setProductId(product_id);

        entity = new HttpEntity<>(reviewInfo, httpHeaders);
        ResponseEntity<ReviewInfo> reviewRes = restTemplate.exchange(
                createUrlWithPort("review/"),
                HttpMethod.POST,
                entity,
                ReviewInfo.class);

        assertNotNull(reviewRes.getBody());
        reviewInfo = reviewRes.getBody();
        return reviewInfo;
    }

    private ResponseEntity<ReviewInfo> findById(Long id) {
        entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<ReviewInfo> findRes = restTemplate.exchange(
                createUrlWithPort("review/" + id),
                HttpMethod.GET,
                entity,
                ReviewInfo.class);
        return findRes;
    }

    private String createUrlWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
