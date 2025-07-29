package com.javatechie.config;

import com.javatechie.activities.TravelActivitiesImpl;
import com.javatechie.workflow.TravelWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemporalConfig {

    @Bean
    public WorkerFactory workerFactory(WorkflowServiceStubs serviceStubs) {
        WorkflowClient client = WorkflowClient.newInstance(serviceStubs);
        WorkerFactory factory = WorkerFactory.newInstance(client);

        Worker worker = factory.newWorker("TRAVEL_TASK_QUEUE");
        worker.registerWorkflowImplementationTypes(TravelWorkflowImpl.class);
        worker.registerActivitiesImplementations(new TravelActivitiesImpl());

        return factory;
    }

    @Bean
    public WorkflowServiceStubs serviceStubs() {
        return WorkflowServiceStubs.newInstance();
    }

    @PostConstruct
    public void startWorker() {
        workerFactory(serviceStubs()).start();
    }
}
