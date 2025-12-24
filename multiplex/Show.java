package multiplex;

import java.util.*;
import multiplex.MultiplexExceptions.*; // Import our custom exceptions

public class Show {
    private Movie movie;
    private Screen screen;
    private double basePrice;
    private Set<Integer> bookedSeats; // Stores taken seats

    public Show(Movie movie, Screen screen, double basePrice) {
        this.movie = movie;
        this.screen = screen;
        this.basePrice = basePrice;
        this.bookedSeats = new HashSet<>();
    }

    // Creating screens via static helper methods (Factory pattern simplified)
    public static Screen createStandardScreen(int id, int seats) {
        return new StandardScreen(id, seats);
    }

    public static Screen createIMAXScreen(int id, int seats) {
        return new IMAXScreen(id, seats);
    }

    // --- The Critical Method ---
    // 'synchronized' prevents two users from booking the same seat at the same time
    public synchronized Booking bookSeats(User user, List<Integer> seats) 
            throws BookingConflictException, InvalidSeatException {
        
        // 1. Check if seats are valid and available
        for (Integer seat : seats) {
            if (seat < 1 || seat > screen.getTotalSeats()) {
                throw new InvalidSeatException("Seat " + seat + " does not exist.");
            }
            if (bookedSeats.contains(seat)) {
                throw new BookingConflictException("Seat " + seat + " is already booked!");
            }
        }

        // 2. Book them
        bookedSeats.addAll(seats);

        // 3. Calculate Cost
        double totalCost = basePrice * screen.getPriceMultiplier() * seats.size();

        return new Booking(user, movie.getTitle(), seats, totalCost);
    }
}