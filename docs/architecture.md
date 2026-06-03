# Architecture Notes

The simulation is organized around a reusable microservice infrastructure and a university compute-resource domain model.

## Message Bus

`MessageBusImpl` coordinates subscriptions and message delivery for events and broadcasts. Services subscribe to specific message types and use callbacks to react to work.

## Services

CPU, GPU, student, conference, and time services communicate through the message bus instead of calling each other directly. This keeps the simulation event-driven and makes each service easier to test in isolation.

## Domain Objects

The objects package models students, models, data, data batches, CPUs, GPUs, conferences, and final output/statistics. Tests under `src/test/java/` exercise several of these core behaviors.

