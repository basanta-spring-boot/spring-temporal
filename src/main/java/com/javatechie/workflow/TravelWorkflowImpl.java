package com.javatechie.workflow;

import com.javatechie.activities.TravelActivities;
import com.javatechie.dto.TravelRequest;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TravelWorkflowImpl implements TravelWorkflow{


    @Override
    public void bookTrip(TravelRequest travelRequest) {

        TravelActivities activities =
                Workflow.newActivityStub(TravelActivities.class,
                        ActivityOptions.newBuilder()
                                .setRetryOptions(RetryOptions.newBuilder()
                                        .setMaximumAttempts(3)
                                        .build())
                                .setStartToCloseTimeout(Duration.ofSeconds(10))
                                .build());


        activities.bookFlight(travelRequest);
        activities.bookHotel(travelRequest);
        activities.arrangeTransport(travelRequest);
        activities.sendConfirmationEmail(travelRequest);
        // The bookTrip method is the main entry point for booking a trip.
        // This method orchestrates the travel booking process by calling various activities.
        // It first books a flight, then a hotel, arranges transport, and finally sends
        // a confirmation email to the user based on the provided travel request.
        // Each activity is called in sequence, ensuring that the travel booking process is completed step by step.
        // The activities are defined in the TravelActivities interface and implemented in the TravelActivitiesImpl
        // class, which handles the actual logic for each step of the travel booking process.
        // The use of Temporal's activity options allows for retries and timeouts, ensuring that the
        // workflow can handle failures gracefully and complete the booking process reliably.
        // The TravelRequest object contains the necessary details such as user ID, destination, and travel date,
        // which are passed to each activity to perform the required operations.
        // This implementation ensures a smooth and efficient travel booking experience for the user.
        // The workflow is defined using Temporal's WorkflowInterface and implemented in this class.
        // The activities are invoked using Temporal's activity stub, which allows for asynchronous execution
        // and provides built-in support for retries and timeouts.
    }
}
