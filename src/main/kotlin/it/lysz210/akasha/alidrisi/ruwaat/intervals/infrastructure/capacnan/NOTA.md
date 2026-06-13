Go rest up! You’ve done the heavy lifting of figuring out the hard design decisions today.

Here is a clean, comprehensive summary you can copy-paste straight into your documentation, task ticket, or notepad. It details exactly what we designed so you can hit the ground running tomorrow.

---

# Architecture & Implementation Design: Activity Saving Pipeline

## 📌 High-Level Architecture Overview

We are implementing a 6-step file orchestration workflow using **Hexagonal Architecture (Ports & Adapters)** in a **Quarkus** application, utilizing **NATS JetStream** for storage and messaging.

The core business logic (Domain Use Case) will act as the orchestrator, while low-level infrastructure concerns (HTTP clients, GZIP compression, and NATS bindings) are strictly isolated inside dedicated, single-responsibility adapters.

---

## 🛠️ Port & Adapter Map

```
[ Driving Adapter: HTTP REST Controller ]
                   │ (POST /activity/{xyz}/save)
                   ▼
       [ Domain Use Case Service ] 
       (Orchestrates steps 1 to 6 using pure Java)
                   │
    ┌──────────────┼──────────────┬──────────────┐
    ▼              ▼              ▼              ▼
 [Port 1]       [Port 2]       [Port 3]       [Port 4]
Downloader    KV Metadata    Blob Storage   Event Publisher
    │              │              │              │
    ▼              ▼              ▼              ▼
[Adapter]      [Adapter]      [Adapter]      [Adapter]
Quarkus REST    NATS KV       NATS ObjectStore NATS JetStream
  Client                     (Does GZIP here!)

```

---

## 📝 The 6-Step Execution Flow (Tomorrow's To-Do List)

### 1. HTTP Trigger (Primary Adapter)

* **Endpoint:** `POST /activity/{xyz}/save`
* **Behavior:** The REST controller catches the request and forwards the payload straight to the inbound domain port: `SaveActivityUseCase.execute(xyz)`.

### 2. Download Data (Outgoing Adapter $\rightarrow$ Remote APIs)

* **Activity Metadata:** Fetched via standard REST client.
* **FIT File:** Fetched via Quarkus REST Client returning an `InputStream`.
* **The Boundary:** The adapter immediately consumes the stream via `is.readAllBytes()` and passes a clean `byte[]` to the domain. Because the file size is capped around **500 KB**, a byte array is highly performant, thread-safe, and safe from memory/GC leakage.

### 3. File Optimization (Infrastructure Layer Exception)

* **The Rule:** Compression is a technical storage optimization to maximize NATS transmission speeds, not a business requirement.
* **The Code:** The domain hands raw bytes to the `FitFileStoragePort`. The `NatsObjectStoreAdapter` intercepts those bytes, compresses them into GZIP on-the-fly, and pushes the `.fit.gz` payload to NATS.

### 4. Metadata Storage (Outgoing Adapter $\rightarrow$ NATS KV)

* The domain coordinates saving the activity metadata.
* The NATS KV Adapter maps the pure domain model to JSON bytes (via Jackson `ObjectMapper`) and performs an upsert into the NATS Key-Value bucket.

### 5. Blob Storage (Outgoing Adapter $\rightarrow$ NATS Object Store)

* **Instance Management:** The NATS `ObjectStore` client is thread-safe. It is configured as a lazy-loaded `@ApplicationScoped` bean wrapper. This protects the Quarkus lifecycle from crashing if the app boots up before the NATS bucket is initialized by a sister microservice.
* **Overwrite Behavior:** By default, NATS `objectStore.put()` performs an overwrite/upsert. If duplicate prevention is needed tomorrow, add a `fitFileStore.getInfo(fileName) == null` check inside the adapter right before writing.

### 6. Event Notification (Outgoing Adapter $\rightarrow$ JetStream Broker)

* Once storage succeeds, the domain triggers the final event port.
* The messaging adapter fires an asynchronous event notification to NATS JetStream to inform downstream analytics services that the file is ready.

---

## 💡 Quick Tips For Tomorrow

* Keep your **Domain Service** completely free of Quarkus annotations (`@Inject`, `@ApplicationScoped`, etc.) and NATS packages (`io.nats.client.*`). Use standard Java constructors for dependency injection.
* Keep your **Ports** minimal. Avoid creating an all-in-one "Super Port" like `ActivityPort.saveEverything()`. Keep them separated based on what infrastructure tool they talk to.

Sleep well, and happy coding tomorrow! You've got a fantastic, clean design ready to build.