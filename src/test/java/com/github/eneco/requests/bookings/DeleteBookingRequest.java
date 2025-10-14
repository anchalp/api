package com.github.eneco.requests.bookings;

import com.github.eneco.requests.BaseRequest;
import com.github.eneco.url.RestfulBookerUrls;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeleteBookingRequest {

    public static Response deleteBookingRequest(int bookingId, String token) {
        return given()
                .spec(BaseRequest.setUp())
                .header("Cookie", "token=" + token)
                .when()
                .delete(RestfulBookerUrls.getBookingUrl(bookingId))
                .then()
                .extract()
                .response();
    }
}
