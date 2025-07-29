package com.javatechie.activities;

import com.javatechie.dto.TravelRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TravelActivitiesImpl implements TravelActivities {

    private int hotelBookingAttempt = 0; // keep track of attempts

    @Override
    public void bookFlight(TravelRequest travelRequest) {
        //REST API call to flight service
        log.info("Flight booked for user: {} to destination: {} on date: {}",
                travelRequest.getUserId(),
                travelRequest.getDestination(),
                travelRequest.getTravelDate());
    }


    // gRPC call to hotel service
    // or REST API call to hotel service
    @Override
    public void bookHotel(TravelRequest travelRequest) {
        hotelBookingAttempt++;
        log.info("🔥 Hotel booking attempt: {}", hotelBookingAttempt);

        if (hotelBookingAttempt < 3) {
            log.error("❌ Hotel booking failed on attempt {}", hotelBookingAttempt);
            throw new RuntimeException("Simulated hotel booking failure!");
        }

        log.info("✅ Hotel booked for user: {} at destination: {} on date: {}",
                travelRequest.getUserId(),
                travelRequest.getDestination(),
                travelRequest.getTravelDate());
    }

    @Override
    public void arrangeTransport(TravelRequest travelRequest) {
        // Kafka message to transport service
        log.info("Transport arranged for user: {} at destination: {} on date: {}",
                travelRequest.getUserId(),
                travelRequest.getDestination(),
                travelRequest.getTravelDate());
    }

    @Override
    public void sendConfirmationEmail(TravelRequest travelRequest) {
        // REST API call to email service
        // or use a messaging system to send confirmation
        log.info("Confirmation email sent to user: {} for travel to destination: {} on date: {}",
                travelRequest.getUserId(),
                travelRequest.getDestination(),
                travelRequest.getTravelDate());
    }
}


