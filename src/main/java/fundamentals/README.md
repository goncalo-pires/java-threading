# Thread Fundamentals – Creation & Coordination

This section covers **fundamental concepts** in Java threading, including **creating threads** and **thread coordination**. These concepts are essential for understanding how to manage multiple threads in a concurrent environment.

---

## 🧠 Key Concepts

### Thread Creation
- **Using the `Runnable` Interface:** Define thread behavior using `Runnable` and pass it to a `Thread` object.
- **Extending the `Thread` Class:** Subclass `Thread` to override the `run()` method.
- **Thread Priorities & Names:** Setting thread priorities and custom names.
- **Exception Handling in Threads:** Managing uncaught exceptions in threads.

### Thread Coordination
- **Thread Interruption:** Signaling a thread to stop its execution.
- **Daemon Threads:** Background threads that do not prevent JVM shutdown.
- **Cooperative Termination:** Properly checking and responding to interruptions within threads.
- **Thread Join:** Waiting for threads to finish execution, with timeout handling.
- **Interrupt Handling:** Ensuring threads respond to interruptions, even during long-running computations.

---

## 📂 Example Breakdown

### 📌 **Thread Creation**

#### 1. **`RunnableThreadExample.java`** (example1)

This example demonstrates how to create threads using the **`Runnable`** interface.

- A worker thread is created using a `Runnable` object and started.
- It sets custom names and priorities for the threads.
- Exception handling is also demonstrated with an uncaught exception handler.

**Key Takeaway:** The `Runnable` interface allows defining thread behavior separately from the `Thread` class. Thread names and priorities can be configured for better control.

---

#### 2. **`ExtendThreadExample.java`** (example2)

Here, a thread is created by **extending the `Thread` class**:

- The `run()` method is overridden in the `CustomThread` class.
- A new thread is created and started, printing a message from the `run()` method.

**Key Takeaway:** Extending `Thread` gives full control over the thread's execution, including the ability to override the `run()` method directly.

---

#### 3. **`VaultCrackingSimulation.java`** (case study)

This case study simulates a **vault cracking** scenario with multiple threads:

- Two hacker threads attempt to guess a vault’s password by brute force.
- A police thread counts down the time for hackers before the police arrive.
- Threads are created by extending the `Thread` class, and priorities are set to give hackers higher priority.

**Key Takeaway:** A real-world case where multiple threads interact and compete. It demonstrates thread priorities, termination with `System.exit(0)`, and managing execution using thread synchronization concepts.

---

### 📌 **Thread Coordination**

#### 1. **`ThreadInterruptExample.java`** (example1)

A simple example showing how to interrupt a thread that is blocked in `Thread.sleep()`:

- A worker thread is started and put to sleep.
- The main thread interrupts the worker, causing it to throw an `InterruptedException`.

**Key Takeaway:** Interrupting a blocked thread allows for controlled termination using exception handling.

---

#### 2. **`DaemonTaskInterruptionExample.java`** (example2)

This demonstrates how **daemon threads** behave when interrupted:

- A long computation (`2^10`) is run in a daemon thread.
- The thread is interrupted, but since no interruption check is inside the loop, it continues running until the JVM shuts down.

**Key Takeaway:** Daemon threads do not prevent JVM shutdown, and they can be abruptly stopped when the application finishes. To handle proper termination, regularly check for interruptions inside the thread.

---

#### 3. **`InterruptingLongComputationTaskExample.java`** (example2)

An improved version of the previous example, where interruption checks are added:

- The computation loop regularly checks for interruption using `Thread.currentThread().isInterrupted()`.
- If interrupted, the thread exits early.

**Key Takeaway:** To handle long-running threads, ensure that they regularly check for interruptions for a responsive exit.

---

#### 4. **`ThreadJoinAndInterruptExample.java`** (example3)

A practical example demonstrating the use of `join()` and thread interruptions:

- Multiple threads are spawned to compute factorials.
- Each thread is waited on using `join(2000)` with a timeout, and interrupted if it takes too long.

**Key Takeaway:** Combine `join()` and interrupts to manage timeouts and ensure threads complete in a controlled manner.

---

## 🗂 Folder Structure

```text
fundamentals/
├── creation/
│   ├── example1/
│   │   └── RunnableThreadExample.java
│   ├── example2/
│   │   └── ExtendThreadExample.java
│   └── caseStudy/
│       └── VaultCrackingSimulation.java
└── coordination/
    ├── example1/
    │   └── ThreadInterruptExample.java
    ├── example2/
    │   ├── DaemonTaskInterruptionExample.java
    │   └── InterruptingLongComputationTaskExample.java
    └── example3/
        └── ThreadJoinAndInterruptExample.java
