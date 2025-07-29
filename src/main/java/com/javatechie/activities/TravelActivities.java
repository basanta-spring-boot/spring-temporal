package com.javatechie.activities;

import com.javatechie.dto.TravelRequest;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface TravelActivities {

    void bookFlight(TravelRequest travelRequest);
    void bookHotel(TravelRequest travelRequest);
    void arrangeTransport(TravelRequest travelRequest);
    void sendConfirmationEmail(TravelRequest travelRequest);
}
