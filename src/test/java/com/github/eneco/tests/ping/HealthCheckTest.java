package com.github.eneco.tests.ping;

import com.github.eneco.requests.BaseRequest;
import com.github.eneco.url.RestfulBookerUrls;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class HealthCheckTest {

    @Test
    @DisplayName("HealthCheck - verify whether API is up and running")
    void pingTest() {
        given()
                .spec(BaseRequest.setUp())
                .when()
                .get(RestfulBookerUrls.getHealthCheckUrl())
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    @DisplayName("HealthCheck - verify API response time is less than 2 seconds")
    void pingResponseTimeTest() {
        given()
                .spec(BaseRequest.setUp())
                .when()
                .get(RestfulBookerUrls.getHealthCheckUrl())
                .then()
                .time(org.hamcrest.Matchers.lessThan(2000L));
    }
}
