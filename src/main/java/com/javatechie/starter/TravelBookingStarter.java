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
}
