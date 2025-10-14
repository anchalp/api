package com.github.eneco.requests.bookings;

import com.github.eneco.dataObject.request.BookingDataObject;
import com.github.eneco.requests.BaseRequest;
import com.github.eneco.url.RestfulBookerUrls;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class PatchBookingRequest {

    public static Response patchBookingRequest(BookingDataObject bookingDataObject, int bookingId, String token){
        return given()
                .spec(BaseRequest.setUp())
                .header("Cookie", "token=" + token)
                .body(bookingDataObject)
                .when()
                .patch(RestfulBookerUrls.getBookingUrl(bookingId))
                .then()
                .extract()
                .response();
    }

    public static Response patchBookingRequest(JSONObject booking, int bookingId, String token){
        return given()
                .spec(BaseRequest.setUp())
                .header("Cookie", "token=" + token)
                .body(booking.toString())
                .when()
                .patch(RestfulBookerUrls.getBookingUrl(bookingId))
                .then()
                .extract()
                .response();
    }
}
