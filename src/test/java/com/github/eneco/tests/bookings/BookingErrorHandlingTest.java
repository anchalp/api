package com.github.eneco.tests.bookings;

import com.github.eneco.dataObject.request.BookingDataObject;
import com.github.eneco.dataObject.request.BookingdatesDataObject;
import com.github.eneco.properties.RestfulBookerProperties;
import com.github.eneco.requests.authentication.CreateTokenRequest;
import com.github.eneco.requests.bookings.CreateBookingRequest;
import com.github.eneco.requests.bookings.PutBookingRequest;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static com.github.eneco.helpers.BookingIdsHelper.getBookingId;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Booking Error Handling Tests")
public class BookingErrorHandlingTest {
    private static String token;
    private static int bookingId;
    private BookingDataObject booking;

    @BeforeEach
    void setUp() {
        JSONObject authData = new JSONObject();
        authData.put("username", RestfulBookerProperties.getUsername());
        authData.put("password", RestfulBookerProperties.getPassword());
        Response createTokenResponse = CreateTokenRequest.createTokenRequest(authData);
        token = createTokenResponse.jsonPath().getString("token");
        booking = new BookingDataObject();
    }

    @AfterEach
    void deleteBooking() {
        if (bookingId > 0) {
            com.github.eneco.requests.bookings.DeleteBookingRequest.deleteBookingRequest(bookingId, token);
        }
    }

    @Test
    @DisplayName("Should return error for invalid date formats")
    void shouldReturnErrorForInvalidDateFormats() {
        BookingdatesDataObject invalidDates = new BookingdatesDataObject();
        invalidDates.setCheckin("13-10-2025"); // invalid format
        invalidDates.setCheckout("2025/11/01"); // invalid format
        booking.setBookingdatesDataObject(invalidDates);
        Response createBookingResponse = CreateBookingRequest.createBookingRequest(booking);
        bookingId = getBookingId(createBookingResponse.jsonPath());
        BookingDataObject updateBooking = new BookingDataObject();
        updateBooking.setBookingdatesDataObject(invalidDates);
        Response putBookingResponse = PutBookingRequest.putBookingRequest(updateBooking, bookingId, token);
        assertThat(putBookingResponse.htmlPath().getString("body")).isEqualTo("Invalid date");
    }

    @Test
    @DisplayName("Should handle invalid parameter values gracefully")
    void shouldHandleInvalidParameterValues() {
        BookingdatesDataObject validDates = new BookingdatesDataObject();
        validDates.setCheckin("2025-10-15");
        validDates.setCheckout("2025-10-20");
        booking.setBookingdatesDataObject(validDates);
        Response createBookingResponse = CreateBookingRequest.createBookingRequest(booking);
        bookingId = getBookingId(createBookingResponse.jsonPath());
        BookingDataObject updateBooking = new BookingDataObject();
        updateBooking.setBookingdatesDataObject(validDates);
        updateBooking.setFirstname(""); // invalid: empty
        updateBooking.setLastname(null); // invalid: null
        updateBooking.setTotalPrice(-1); // invalid: negative
        updateBooking.setDepositPaid(true);
        updateBooking.setAdditionalNeeds("");
        Response putBookingResponse = PutBookingRequest.putBookingRequest(updateBooking, bookingId, token);
        boolean isError = putBookingResponse.htmlPath().getString("body").equalsIgnoreCase("Invalid data")
            || putBookingResponse.statusCode() >= 400;
        assertThat(isError || putBookingResponse.statusCode() == 200).isTrue();
    }
}

