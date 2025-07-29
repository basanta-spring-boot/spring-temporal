package com.javatechie.controller;

import com.javatechie.dto.TravelRequest;
import com.javatechie.starter.TravelBookingStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/travel")
public class TravelBookingController {

    private final TravelBookingStarter starter;

    @Autowired
    public TravelBookingController(TravelBookingStarter starter) {
        this.starter = starter;
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookTravel(@RequestBody TravelRequest travelRequest) {
        starter.startWorkflow(travelRequest);
        return ResponseEntity.ok("Travel booking workflow started for user: " + travelRequest.getUserId());
    }
}
