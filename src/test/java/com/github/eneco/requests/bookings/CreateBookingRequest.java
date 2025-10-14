package com.github.eneco.requests.bookings;

import com.github.eneco.dataObject.request.BookingDataObject;
import com.github.eneco.requests.BaseRequest;
import com.github.eneco.url.RestfulBookerUrls;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CreateBookingRequest {

    public static Response createBookingRequest(BookingDataObject bookingDataObject){
        return given()
                .spec(BaseRequest.setUp())
                .body(bookingDataObject)
                .when()
                .post(RestfulBookerUrls.createBookingUrl())
                .then()
                .extract()
                .response();
    }
}
