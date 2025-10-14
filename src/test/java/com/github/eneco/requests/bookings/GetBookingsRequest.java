package com.github.eneco.requests.bookings;

import com.github.eneco.requests.BaseRequest;
import com.github.eneco.url.RestfulBookerUrls;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetBookingsRequest {
    // Get all bookings
    public static Response getBookingRequest(){
        return given()
                .spec(BaseRequest.setUp())
                .when()
                .get(RestfulBookerUrls.getBookingsUrl())
                .then()
                .extract()
                .response();
    }

    // Get bookings with query parameters
    public static Response getBookingRequestWithQueryParams(Map<String, String> queryParams){
        return given()
                .spec(BaseRequest.setUp())
                .when()
                .queryParams(queryParams)
                .get(RestfulBookerUrls.getBookingsUrl())
                .then()
                .extract()
                .response();
    }
}
