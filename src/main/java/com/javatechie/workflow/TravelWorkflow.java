package com.javatechie.workflow;

import com.javatechie.dto.TravelRequest;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface TravelWorkflow {

    @WorkflowMethod
    void bookTrip(TravelRequest travelRequest);
    // This method will be used to book a trip based on the provided travel request.

    @SignalMethod
    void sendConfirmationSignal();
    // This method will be used to send a confirmation signal to the workflow, indicating that the user has confirmed the booking.
    // It will update the internal state of the workflow to reflect that the user has confirmed the booking.
}
