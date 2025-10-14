package com.github.eneco.helpers;

import com.github.eneco.dataObject.request.BookingDataObject;
import io.restassured.path.json.JsonPath;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.github.eneco.helpers.JsonHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationHelper {

    public static void verifyCreateResponseContainsCorrectData(JsonPath responseJsonPath, BookingDataObject requestBooking) {
        assertThat(responseJsonPath.getString(BOOKING + FIRSTNAME)).isEqualTo(requestBooking.getFirstname());
        assertThat(responseJsonPath.getString(BOOKING + LASTNAME)).isEqualTo(requestBooking.getLastname());
        assertThat(responseJsonPath.getInt(BOOKING + TOTAL_PRICE)).isEqualTo(requestBooking.getTotalPrice());
        assertThat(responseJsonPath.getBoolean(BOOKING + DEPOSIT_PAID)).isEqualTo(requestBooking.isDepositPaid());
        assertThat(responseJsonPath.getString(BOOKING_BOOKING_DATES + CHECKIN)).isEqualTo(requestBooking.getBookingdatesDataObject().getCheckin());
        assertThat(responseJsonPath.getString(BOOKING_BOOKING_DATES + CHECKOUT)).isEqualTo(requestBooking.getBookingdatesDataObject().getCheckout());
        assertThat(responseJsonPath.getString(BOOKING + ADDITIONAL_NEEDS)).isEqualTo(requestBooking.getAdditionalNeeds());
    }

    public static void verifyResponseContainsCorrectData(JsonPath jsonPath, BookingDataObject bookingDataObject) {
        assertThat(jsonPath.getString(FIRSTNAME)).isEqualTo(bookingDataObject.getFirstname());
        assertThat(jsonPath.getString(LASTNAME)).isEqualTo(bookingDataObject.getLastname());
        assertThat(jsonPath.getInt(TOTAL_PRICE)).isEqualTo(bookingDataObject.getTotalPrice());
        assertThat(jsonPath.getBoolean(DEPOSIT_PAID)).isEqualTo(bookingDataObject.isDepositPaid());
        assertThat(jsonPath.getString(BOOKING_DATES + "." + CHECKIN)).isEqualTo(bookingDataObject.getBookingdatesDataObject().getCheckin());
        assertThat(jsonPath.getString(BOOKING_DATES + "." + CHECKOUT)).isEqualTo(bookingDataObject.getBookingdatesDataObject().getCheckout());
        assertThat(jsonPath.getString(ADDITIONAL_NEEDS)).isEqualTo(bookingDataObject.getAdditionalNeeds());
    }
}
