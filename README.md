# Simulation of Managing University Compute Resources

This Java project simulates a university compute-resource system for training and testing student research models. It uses a microservices message bus, CPU and GPU services, student services, conference services, and a time service to coordinate model training, testing, publishing, and statistics collection.

The repository is useful for reviewing concurrent Java design, message-passing architecture, event and broadcast handling, and unit testing around shared service infrastructure.

## Tech Stack

- Java 8
- Maven
- JUnit
- Gson and JSON parsing libraries
- Microservice/event-bus architecture

## Quick Start

Install Java 8 or newer and Maven, then run:

```bash
mvn test
```

To compile the project without running tests:

```bash
mvn compile
```

## Usage

The main application entry point is:

```text
src/main/java/bgu/spl/mics/application/CRMSRunner.java
```

Run it with the JSON input file expected by the assignment implementation:

```bash
mvn exec:java -Dexec.mainClass="bgu.spl.mics.application.CRMSRunner" -Dexec.args="path/to/input.json"
```

## Testing

The repository includes unit tests for the message bus, future implementation, CPU behavior, and GPU behavior under `src/test/java/`.

```bash
mvn test
```

## Project Structure

- `src/main/java/bgu/spl/mics/`: reusable message bus, microservice, callback, event, and broadcast interfaces.
- `src/main/java/bgu/spl/mics/application/objects/`: domain objects such as students, models, CPUs, GPUs, conferences, and statistics.
- `src/main/java/bgu/spl/mics/application/services/`: runtime microservices that coordinate training, testing, publishing, and termination.
- `src/main/java/bgu/spl/mics/application/messages/`: events and broadcasts passed through the message bus.
- `src/test/java/`: focused unit tests for core concurrency and domain behavior.

## Demo / Walkthrough

This is a command-line simulation rather than a hosted web demo. A reviewer can inspect the flow by running the Maven tests, then running `CRMSRunner` with an assignment-style JSON input file to generate simulation output.

