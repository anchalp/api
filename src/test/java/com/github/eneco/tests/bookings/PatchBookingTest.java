package com.github.eneco.tests.bookings;

import com.github.eneco.dataObject.request.BookingdatesDataObject;
import com.github.eneco.dataObject.request.BookingDataObject;
import com.github.eneco.properties.RestfulBookerProperties;
import com.github.eneco.requests.authentication.CreateTokenRequest;
import com.github.eneco.requests.bookings.CreateBookingRequest;
import com.github.eneco.requests.bookings.DeleteBookingRequest;
import com.github.eneco.requests.bookings.PatchBookingRequest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.eneco.helpers.BookingIdsHelper.getBookingId;
import static com.github.eneco.helpers.JsonHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PatchBookingTest {

    private static String token;
    private static int bookingId;

    private BookingDataObject booking;
    private BookingDataObject updateBooking;

    @BeforeEach
    void auth() {
        JSONObject authData = new JSONObject();
        authData.put(USERNAME, RestfulBookerProperties.getUsername());
        authData.put(PASSWORD, RestfulBookerProperties.getPassword());
        Response createTokenResponse = CreateTokenRequest.createTokenRequest(authData);
        token = createTokenResponse.jsonPath().getString(TOKEN);

        booking = new BookingDataObject();
        updateBooking = new BookingDataObject();
    }

    @AfterEach
    void deleteBooking() {
        Response deleteBookingResponse = DeleteBookingRequest.deleteBookingRequest(bookingId, token);
        assertThat(deleteBookingResponse.statusCode()).isEqualTo(HttpStatus.SC_CREATED);
    }

    @Test
    @DisplayName("Partially update booking with valid firstname")
    void partiallyUpdateBookingWithValidFirstnameTest() {
        Response createBookingResponse = CreateBookingRequest.createBookingRequest(booking);
        JsonPath createBookingJsonPath = createBookingResponse.jsonPath();
        bookingId = getBookingId(createBookingJsonPath);

        String updatedFirstname = "Lila";
        updateBooking.setFirstname(updatedFirstname);

        Response patchBookingResponse = PatchBookingRequest.patchBookingRequest(updateBooking, bookingId, token);

        assertThat(patchBookingResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(patchBookingResponse.jsonPath().getString(FIRSTNAME)).isEqualTo(updatedFirstname);
    }

    @Test
    @DisplayName("Partially update booking with valid lastname")
    void partiallyUpdateBookingWithValidLastnameTest() {
        Response createBookingResponse = CreateBookingRequest.createBookingRequest(booking);
        JsonPath createBookingJsonPath = createBookingResponse.jsonPath();
        bookingId = getBookingId(createBookingJsonPath);

        String updatedLastname = "Jones";
        updateBooking.setLastname(updatedLastname);

        Response patchBookingResponse = PatchBookingRequest.patchBookingRequest(updateBooking, bookingId, token);

        assertThat(patchBookingResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(patchBookingResponse.jsonPath().getString(LASTNAME)).isEqualTo(updatedLastname);
    }

    @Test
    @DisplayName("Partially update booking with valid total price")
    void partiallyUpdateBookingWithValidTotalPriceValueTest() {
        Response createBookingResponse = CreateBookingRequest.createBookingRequest(booking);
        JsonPath createBookingJsonPath = createBookingResponse.jsonPath();
        bookingId = getBookingId(createBookingJsonPath);

        int updatedTotalPrice = 2678;
        updateBooking.setTotalPrice(updatedTotalPrice);

        Response patchBookingResponse = PatchBookingRequest.patchBookingRequest(updateBooking, bookingId, token);

        assertThat(patchBookingResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(patchBookingResponse.jsonPath().getInt(TOTAL_PRICE)).isEqualTo(updatedTotalPrice);
    }

    @Test
    @DisplayName("Partially update booking with valid deposit paid")
    void partiallyUpdateBookingWithValidDepositPaidValue() {
        Response createBookingResponse = CreateBookingRequest.createBookingRequest(booking);
        JsonPath createBookingJsonPath = createBookingResponse.jsonPath();
        bookingId = getBookingId(createBookingJsonPath);

        boolean updatedDepositPaid = false;
        updateBooking.setDepositPaid(updatedDepositPaid);

        Response patchBookingResponse = PatchBookingRequest.patchBookingRequest(updateBooking, bookingId, token);

        assertThat(patchBookingResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(patchBookingResponse.jsonPath().getBoolean(DEPOSIT_PAID)).isEqualTo(updatedDepositPaid);
    }

    @Test
    @DisplayName("Partially update booking with valid checkin date")
    void partiallyUpdateBookingWithValidCheckinDateTest() {
        Response createBookingResponse = CreateBookingRequest.createBookingRequest(booking);
        JsonPath createBookingJsonPath = createBookingResponse.jsonPath();
        bookingId = getBookingId(createBookingJsonPath);

        BookingdatesDataObject bookingdatesDataObject = new BookingdatesDataObject();
        String updatedCheckin = "2022-11-20";
        bookingdatesDataObject.setCheckin(updatedCheckin);
        updateBooking.setBookingdatesDataObject(bookingdatesDataObject);

        Response patchBookingResponse = PatchBookingRequest.patchBookingRequest(updateBooking, bookingId, token);

        assertThat(patchBookingResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(patchBookingResponse.jsonPath().getString(BOOKING_DATES + "." + CHECKIN)).isEqualTo(updatedCheckin);
    }

    @Test
    @DisplayName("Partially update booking with valid checkout date")
    void partiallyUpdateBookingWithValidCheckoutDateTest() {
        Response createBookingResponse = CreateBookingRequest.createBookingRequest(booking);
        JsonPath createBookingJsonPath = createBookingResponse.jsonPath();
        bookingId = getBookingId(createBookingJsonPath);

        BookingdatesDataObject bookingdatesDataObject = new BookingdatesDataObject();
        String updatedCheckout = "2022-10-31";
        bookingdatesDataObject.setCheckout(updatedCheckout);
        updateBooking.setBookingdatesDataObject(bookingdatesDataObject);

        Response patchBookingResponse = PatchBookingRequest.patchBookingRequest(updateBooking, bookingId, token);

        assertThat(patchBookingResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(patchBookingResponse.jsonPath().getString(BOOKING_DATES + "." + CHECKOUT)).isEqualTo(updatedCheckout);
    }

    @Test
    @DisplayName("Partially update booking with valid additional needs")
    void partiallyUpdateBookingWithValidAdditionalNeedsTest() {
        Response createBookingResponse = CreateBookingRequest.createBookingRequest(booking);
        JsonPath createBookingJsonPath = createBookingResponse.jsonPath();
        bookingId = getBookingId(createBookingJsonPath);

        String updatedAdditionalNeeds = "Breakfast in the hotel room";
        updateBooking.setAdditionalNeeds(updatedAdditionalNeeds);

        Response patchBookingResponse = PatchBookingRequest.patchBookingRequest(updateBooking, bookingId, token);

        assertThat(patchBookingResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(patchBookingResponse.jsonPath().getString(ADDITIONAL_NEEDS)).isEqualTo(updatedAdditionalNeeds);
    }

    @Test
    @DisplayName("Partially update booking with two valid fields - firstname and additional needs")
    void partiallyUpdateBookingWithTwoValidFieldsTest() {
        Response createBookingResponse = CreateBookingRequest.createBookingRequest(booking);
        JsonPath createBookingJsonPath = createBookingResponse.jsonPath();
        bookingId = getBookingId(createBookingJsonPath);

        String updatedFirstname = "Bobby";
        String updatedAdditionalNeeds = "Extra bedding in room for kid";
        updateBooking.setFirstname(updatedFirstname);
        updateBooking.setAdditionalNeeds(updatedAdditionalNeeds);

        Response patchBookingResponse = PatchBookingRequest.patchBookingRequest(updateBooking, bookingId, token);

        assertThat(patchBookingResponse.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(patchBookingResponse.jsonPath().getString(FIRSTNAME)).isEqualTo(updatedFirstname);
        assertThat(patchBookingResponse.jsonPath().getString(ADDITIONAL_NEEDS)).isEqualTo(updatedAdditionalNeeds);
    }
}
