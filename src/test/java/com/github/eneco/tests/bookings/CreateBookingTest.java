package com.github.eneco.tests.bookings;

import com.github.eneco.dataObject.request.BookingdatesDataObject;
import com.github.eneco.dataObject.request.BookingDataObject;
import com.github.eneco.properties.RestfulBookerProperties;
import com.github.eneco.requests.authentication.CreateTokenRequest;
import com.github.eneco.requests.bookings.CreateBookingRequest;
import com.github.eneco.requests.bookings.DeleteBookingRequest;
import com.github.eneco.requests.bookings.GetBookingRequest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.eneco.helpers.BookingIdsHelper.getBookingId;
import static com.github.eneco.helpers.JsonHelper.*;
import static com.github.eneco.helpers.ValidationHelper.verifyCreateResponseContainsCorrectData;
import static com.github.eneco.helpers.ValidationHelper.verifyResponseContainsCorrectData;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class CreateBookingTest {

    private static String token;
    private static int bookingId;

    @BeforeEach
    void auth() {
        JSONObject authData = new JSONObject();
        authData.put(USERNAME, RestfulBookerProperties.getUsername());
        authData.put(PASSWORD, RestfulBookerProperties.getPassword());
        Response createTokenResponse = CreateTokenRequest.createTokenRequest(authData);
        token = createTokenResponse.jsonPath().getString(TOKEN);
    }

    @AfterEach
    void deleteBooking() {
        log.info("Deleting booking id: {}", bookingId);
        Response response = DeleteBookingRequest.deleteBookingRequest(bookingId, token);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_CREATED);
    }

    @Test
    @DisplayName("Create booking with invalid dates (the only validation implemented)")
    void createBookingWithInvalidDatesTest() {
        BookingDataObject booking = new BookingDataObject();
        BookingdatesDataObject bookingdatesDataObject = new BookingdatesDataObject();
        bookingdatesDataObject.setCheckin("20221012");
        bookingdatesDataObject.setCheckout("202210-12");
        booking.setBookingdatesDataObject(bookingdatesDataObject);
        Response createBookingResponse = CreateBookingRequest.createBookingRequest(booking);
        assertThat(createBookingResponse.htmlPath().getString("body")).isEqualTo("Invalid date");
    }

    @Test
    @DisplayName("Create booking with valid data")
    void createBookingWithValidData() {
        BookingDataObject booking = new BookingDataObject();
        Response createBookingResponse = CreateBookingRequest.createBookingRequest(booking);
        JsonPath createBookingJsonPath = createBookingResponse.jsonPath();
        bookingId = getBookingId(createBookingJsonPath);

        assertThat(createBookingResponse.statusCode()).isEqualTo(HttpStatus.SC_OK);
        verifyCreateResponseContainsCorrectData(createBookingJsonPath, booking);

        Response getBookingResponse = GetBookingRequest.getBookingRequest(bookingId);
        verifyResponseContainsCorrectData(getBookingResponse.jsonPath(), booking);
    }

    @Test
    @DisplayName("Create booking with All bank details empty")
    void createBookingWithValidData1() {
        BookingDataObject booking = new BookingDataObject();
        booking.setFirstname("");
        booking.setLastname("");
        booking.setTotalPrice(0);
        booking.setDepositPaid(false);
        booking.setAdditionalNeeds("");
        BookingdatesDataObject bookingdatesDataObject = new BookingdatesDataObject();
        bookingdatesDataObject.setCheckin("");
        bookingdatesDataObject.setCheckout("");
        Response createBookingResponse = CreateBookingRequest.createBookingRequest(booking);
        JsonPath createBookingJsonPath = createBookingResponse.jsonPath();
        bookingId = getBookingId(createBookingJsonPath);

        assertThat(createBookingResponse.statusCode()).isEqualTo(HttpStatus.SC_OK);
        verifyCreateResponseContainsCorrectData(createBookingJsonPath, booking);

        Response getBookingResponse = GetBookingRequest.getBookingRequest(bookingId);
        verifyResponseContainsCorrectData(getBookingResponse.jsonPath(), booking);
    }
}
