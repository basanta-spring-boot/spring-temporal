package com.javatechie.activities;

import com.javatechie.dto.TravelRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TravelActivitiesImpl implements TravelActivities {

   // private int hotelBookingAttempt = 0; // keep track of attempts

    @Override
    public void bookFlight(TravelRequest travelRequest) {
        //REST API call to flight service
        log.info("Flight booked for user: {} to destination: {} on date: {}",
                travelRequest.getUserId(),
                travelRequest.getDestination(),
                travelRequest.getTravelDate());
    }

    @Override
    public void cancelFlight(TravelRequest request) {
        log.info("🛑 Cancelling flight for user {} because of failure ", request.getUserId());
    }


    // gRPC call to hotel service
    // or REST API call to hotel service
    @Override
    public void bookHotel(TravelRequest travelRequest) {

        log.info("✅ Hotel booked for user: {} at destination: {} on date: {}",
                travelRequest.getUserId(),
                travelRequest.getDestination(),
                travelRequest.getTravelDate());
    }

    @Override
    public void cancelHotel(TravelRequest request) {
        log.info("🛑 Cancelling hotel for user {} because of failure", request.getUserId());
    }

    @Override
    public void arrangeTransport(TravelRequest travelRequest) {
        // Kafka message to transport service
        log.info("Transport arranged for user: {} at destination: {} on date: {}",
                travelRequest.getUserId(),
                travelRequest.getDestination(),
                travelRequest.getTravelDate());

        // Simulate a failure to demonstrate compensation
        //throw new RuntimeException("Simulated transport arrangement failure!");
    }

    @Override
    public void cancelTransport(TravelRequest request) {
        log.info("🛑 Cancelling transport for user {}", request.getUserId());
    }

    @Override
    public void sendConfirmationEmail(TravelRequest travelRequest) {
        // REST API call to email service
        // or use a messaging system to send confirmation
        log.info("Booking status sent to user: {} ", travelRequest.getUserId());
    }

    @Override
    public void confirmBooking(TravelRequest travelRequest) {
        log.info("Booking confirmed for user: {} to destination: {} on date: {}",
                travelRequest.getUserId(),
                travelRequest.getDestination(),
                travelRequest.getTravelDate());
    }

    @Override
    public void cancelBooking(TravelRequest travelRequest) {
        log.info("Booking cancelled for user: {} to destination: {} on date: {}",
                travelRequest.getUserId(),
                travelRequest.getDestination(),
                travelRequest.getTravelDate());
        // Logic to cancel the booking, e.g., REST API call to booking service
    }
}


