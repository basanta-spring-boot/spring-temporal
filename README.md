code

```
// We'll break this example into three parts:
// 1. Travel Orchestrator (Spring Boot + Temporal Workflows)
// 2. Microservices:
//    - FlightService (REST)
//    - HotelService (gRPC)
//    - TransportService (Kafka Consumer)
// 3. Messaging/Communication setup

// --- TRAVEL ORCHESTRATOR (Temporal Workflow) ---
```


Component	URL
Temporal Web UI	http://localhost:8088
Temporal Server	SDK connects on localhost:7233
Postgres	Runs on localhost:5432
