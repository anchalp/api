package com.github.eneco.requests.bookings;

import com.github.eneco.requests.BaseRequest;
import com.github.eneco.url.RestfulBookerUrls;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetBookingRequest {

    public static Response getBookingRequest(int bookingId){
        return given()
                .spec(BaseRequest.setUp())
                .when()
                .get(RestfulBookerUrls.getBookingUrl(bookingId))
                .then()
                .extract()
                .response();
    }
}

