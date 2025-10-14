package com.github.eneco.helpers;

import com.github.eneco.requests.bookings.GetBookingsRequest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Collections;
import java.util.List;

import static com.github.eneco.helpers.JsonHelper.ID;

public class BookingIdsHelper {

    public static int getBookingId(JsonPath jsonPath) {
        return Integer.parseInt(jsonPath.getString(ID));
    }

    public static int getExistingId() {
        List<Integer> bookingIds = getBookingIds();
        Collections.shuffle(bookingIds);
        return bookingIds.get(0);
    }

    public static int getNonExistingId() {
        List<Integer> bookingIds = getBookingIds();
        Collections.sort(bookingIds);
        return bookingIds.get((bookingIds.size()-1)) + 100;
    }

    private static List<Integer> getBookingIds() {
        Response getBookingIdsResponse = GetBookingsRequest.getBookingRequest();
        JsonPath jsonPath = getBookingIdsResponse.jsonPath();
        return jsonPath.getList(ID);
    }
}
