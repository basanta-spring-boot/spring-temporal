package com.javatechie.starter;

import com.javatechie.dto.TravelRequest;
import com.javatechie.workflow.TravelWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TravelBookingStarter {

    @Autowired
    private WorkflowServiceStubs serviceStubs;

    /**
     * Starts a new travel booking workflow for the given request.
     * Initializes the workflow with a unique ID based on the user and submits the booking request.
     *
     * @param request the travel booking details
     */
    public void startWorkflow(TravelRequest request) {
        WorkflowClient client = WorkflowClient.newInstance(serviceStubs);

        TravelWorkflow workflow = client.newWorkflowStub(
            TravelWorkflow.class,
            WorkflowOptions.newBuilder()
                .setTaskQueue("TRAVEL_TASK_QUEUE")
                .setWorkflowId("travel_" + request.getUserId())
                .build()
        );

        WorkflowClient.start(workflow::bookTrip, request);
    }

    public void sendConfirmationSignal(String userId) {
        WorkflowClient client = WorkflowClient.newInstance(serviceStubs);

        String workflowId = "travel_" + userId;
        TravelWorkflow workflow = client.newWorkflowStub(TravelWorkflow.class, workflowId);

        workflow.sendConfirmationSignal();
    }
}
