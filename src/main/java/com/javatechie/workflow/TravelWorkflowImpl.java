package com.javatechie.workflow;

import com.javatechie.activities.TravelActivities;
import com.javatechie.dto.TravelRequest;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Saga;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
public class TravelWorkflowImpl implements TravelWorkflow {

    private boolean isUserConfirmed = false;

    @SignalMethod
    public void sendConfirmationSignal() {
        log.info("📩 Received user confirmation signal.");
        isUserConfirmed = true;
    }

    @Override
    public void bookTrip(TravelRequest travelRequest) {

        log.info("🚀 Starting travel booking for user: {}", travelRequest.getUserId());

        /*
          This code creates a Temporal activity stub for the TravelActivities interface,
          configuring it to retry failed activities up to 3 times and setting a timeout
          of 10 seconds for each activity execution.
          This ensures reliable and time-bound execution of travel-related activities within the workflow.
        */
        TravelActivities activities =
                Workflow.newActivityStub(TravelActivities.class,
                        ActivityOptions.newBuilder()
                                .setRetryOptions(RetryOptions.newBuilder()
                                        .setMaximumAttempts(3)
                                        .build())
                                .setStartToCloseTimeout(Duration.ofSeconds(10))
                                .build());

        Saga.Options sagaOptions = new Saga.Options.Builder()
                .setParallelCompensation(false)
                .build();

        Saga saga = new Saga(sagaOptions);
        try {
            activities.bookFlight(travelRequest);
            saga.addCompensation(() -> activities.cancelFlight(travelRequest));

            activities.bookHotel(travelRequest);
            saga.addCompensation(() -> activities.cancelHotel(travelRequest));

            activities.arrangeTransport(travelRequest);
            saga.addCompensation(() -> activities.cancelTransport(travelRequest));

            log.info("⏳ Waiting up to 2 minutes for user confirmation...");
            boolean isConfirmed = Workflow
                    .await(
                            Duration.ofMinutes(2),
                            () -> isUserConfirmed
                    );

            if (!isConfirmed) {
                log.warn("❌ User did not confirm booking within 2 minutes. Cancelling booking.");
                activities.cancelBooking(travelRequest);
                activities.sendConfirmationEmail(travelRequest);
            } else {
                log.info("✅ User confirmed booking. Proceeding with confirmation.");
                activities.confirmBooking(travelRequest);
                activities.sendConfirmationEmail(travelRequest);
            }

        } catch (Exception e) {
            log.error("❌ Error during travel booking workflow: {}", e.getMessage());
            saga.compensate();
        }

        log.info("📦 Travel booking workflow completed for user: {}", travelRequest.getUserId());
    }
}
        /*
         The bookTrip method is the main entry point for booking a trip.
         This method orchestrates the travel booking process by calling various activities.
         It first books a flight, then a hotel, arranges transport, and finally sends
         a confirmation email to the user based on the provided travel request.
         Each activity is called in sequence, ensuring that the travel booking process is completed step by step.
         The activities are defined in the TravelActivities interface and implemented in the TravelActivitiesImpl
         class, which handles the actual logic for each step of the travel booking process.
         The use of Temporal's activity options allows for retries and timeouts, ensuring that the
         workflow can handle failures gracefully and complete the booking process reliably.
         The TravelRequest object contains the necessary details such as user ID, destination, and travel date,
         which are passed to each activity to perform the required operations.
         This implementation ensures a smooth and efficient travel booking experience for the user.
         The workflow is defined using Temporal's WorkflowInterface and implemented in this class.
         The activities are invoked using Temporal's activity stub, which allows for asynchronous execution
         and provides built-in support for retries and timeouts.
        */

