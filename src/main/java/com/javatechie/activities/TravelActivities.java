package com.javatechie.activities;

import com.javatechie.dto.TravelRequest;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface TravelActivities {

    void bookFlight(TravelRequest travelRequest);
    void cancelFlight(TravelRequest request);
    void bookHotel(TravelRequest travelRequest);
    void cancelHotel(TravelRequest request);
    void arrangeTransport(TravelRequest travelRequest);
    void cancelTransport(TravelRequest request);
    void confirmBooking(TravelRequest travelRequest);
    void cancelBooking(TravelRequest travelRequest);
    void sendConfirmationEmail(TravelRequest travelRequest);

}
