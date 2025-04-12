# Java Threading

## Introduction

The **Java Threading** repository is a personal project aimed at documenting my journey in learning **Java concurrency**, **multithreading**, and **performance optimization**. This repo serves as a collection of code snippets, examples, and projects that explore various aspects of Java's multithreading capabilities. The objective is to deepen my understanding of concurrent programming and to improve performance through effective use of Java threading mechanisms.

## Objectives

- To learn how to implement multithreading in Java using various classes and interfaces.
- To understand and apply concepts of thread safety, synchronization, and thread pools.
- To explore performance optimization in multithreaded Java applications.
- To apply knowledge through practical case studies and examples.

## Project Structure

The project is divided into several folders based on the specific areas of learning. Below is an explanation of each section:

### 1. Threading Fundamentals

This section covers the basics of thread creation, coordination, and debugging in Java.

#### 1.1 Thread Creation

- **Thread creation with `Runnable` Interface**  
- **Thread creation with `java.lang.Thread`**  
- **Thread class capabilities**  
- **Case Study: Interactive Multithreading Application**  

#### 1.2 Thread Coordination

- **Thread termination**
- **Thread interrupt**
- **Daemon threads**
- **Thread coordination with thread.join()**
- **Case study**

### 2. Performance Optimization

This section explores how to define and measure performance in multithreaded applications, with a focus on minimizing latency and maximizing throughput.

- **Performance criteria/definition**  
  Performance in a multithreaded environment is evaluated by how efficiently the system utilizes resources (CPU, memory, threads) to execute tasks concurrently, while maintaining responsiveness and minimizing bottlenecks.

- **Performance in multithreaded applications**  
  In multithreading, performance is influenced by how well threads are managed and coordinated. Proper synchronization, load balancing, and minimizing contention for shared resources are key to achieving optimal performance. A well-designed multithreaded system can reduce idle CPU time and improve responsiveness under heavy workloads.

- **Latency**  
  The time it takes to complete a single task, measured in time units (e.g., milliseconds). Lower latency means faster response or completion time for individual tasks.

- **Throughput**  
  The number of tasks completed in a given time period, typically measured in tasks per second (tasks/time unit). Higher throughput indicates the system can handle more work concurrently.
