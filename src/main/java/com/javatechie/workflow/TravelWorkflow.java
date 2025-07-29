package com.javatechie.workflow;

import com.javatechie.dto.TravelRequest;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface TravelWorkflow {

    @WorkflowMethod
    void bookTrip(TravelRequest travelRequest);
    // This method will be used to book a trip based on the provided travel request.
}
