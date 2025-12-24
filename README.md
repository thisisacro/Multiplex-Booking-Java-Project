# Multiplex Booking System

A thread-safe, console-based movie ticket booking system built in Java. This project demonstrates core backend concepts including **Multithreading**, **Concurrency Control**, **Object-Oriented Programming (OOP)**, and **Custom Exception Handling**.

## Features

* **Concurrency Handling**: Simulates real-world booking scenarios where multiple users try to book the same seat simultaneously. Uses `synchronized` blocks to prevent double-booking.
* **Dynamic Pricing**: Implements polymorphism to calculate ticket prices based on screen type (e.g., standard screens vs. IMAX screens).
* **Data Integrity**: Uses custom exceptions (`BookingConflictException`, `InvalidSeatException`) to handle edge cases and invalid inputs safely.
* **Modular Design**: Structured using Java packages to separate the model (Entities), logic (Show), and execution (Main).


## Tech Stack

* **Language**: Java (JDK 8+)
* **Concepts**: Multithreading, Synchronization, OOP (Inheritance, Polymorphism, Encapsulation), Exception Handling.

## How It Works

1.  **Screen Setup**: The system creates different screen types (Standard or IMAX). IMAX screens automatically apply a price multiplier.
2.  **Show Creation**: A `Show` object is created, linking a `Movie` to a `Screen` at a specific time.
3.  **Booking Process**:
    * A `User` requests a list of seats.
    * The `Show` class locks the booking method (Critical Section).
    * It checks if *any* of the requested seats are already in the `bookedSeats` set.
    * **If available**: The seats are added to the set, and a `Booking` receipt is returned.
    * **If unavailable**: A `BookingConflictException` is thrown, preventing the transaction.

## How to Run

1.  **Clone the repository**:
    ```bash
    git clone [https://github.com/YOUR_USERNAME/MultiplexBookingSystem.git](https://github.com/YOUR_USERNAME/MultiplexBookingSystem.git)
    cd MultiplexBookingSystem
    ```

2.  **Compile the code**:
    ```bash
    javac multiplex/*.java Main.java
    ```

3.  **Run the application**:
    ```bash
    java Main
    ```

## Simulation Scenario

The `Main.java` file runs a concurrency stress test:
* **User A (Alice)** attempts to book seats `[5, 6]`.
* **User B (Bob)** attempts to book seats `[6, 7]`.
* Since both request seat `6`, the system ensures only the first thread to access the synchronized block succeeds. The second user receives a "Booking Conflict" error.

## Example Output

Alice is trying to book seats: [5, 6]
Bob is trying to book seats: [6, 7]
SUCCESS for Alice: Receipt: [User: Alice, Movie: Avatar 2, Seats: [5, 6], Cost: $40.0]
FAILURE for Bob: Seat 6 is already booked!
