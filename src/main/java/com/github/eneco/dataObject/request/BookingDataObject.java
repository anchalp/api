package com.github.eneco.dataObject.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookingDataObject {
    private String firstname = "Anchal";
    private String lastname = "Pandey";
    @JsonProperty("totalprice")
    private int totalPrice = 9999;
    @JsonProperty("depositpaid")
    private boolean depositPaid = true;
    @JsonProperty("bookingdates")
    private BookingdatesDataObject bookingdatesDataObject = new BookingdatesDataObject();
    @JsonProperty("additionalneeds")
    private String additionalNeeds = "Lunch";
}

