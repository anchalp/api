package com.github.eneco.requests.bookings;

import com.github.eneco.dataObject.request.BookingDataObject;
import com.github.eneco.requests.BaseRequest;
import com.github.eneco.url.RestfulBookerUrls;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PutBookingRequest {

    public static Response putBookingRequest(BookingDataObject bookingDataObject, int bookingId, String token){
        return given()
                .spec(BaseRequest.setUp())
                .header("Cookie", "token=" + token)
                .body(bookingDataObject)
                .when()
                .put(RestfulBookerUrls.getBookingUrl(bookingId))
                .then()
                .extract()
                .response();
    }
}
