package multiplex;

import multiplex.MultiplexExceptions.*;

public class Show {
    private Movie movie;
    private Screen screen;
    private double basePrice;
    
    // PRIMITIVE ARRAY: index = seat number, value = is booked?
    // Size will be totalSeats + 1 (so we can use index 1 for seat 1)
    private boolean[] seatStatus; 

    public Show(Movie movie, Screen screen, double basePrice) {
        this.movie = movie;
        this.screen = screen;
        this.basePrice = basePrice;
        // Initialize array. Default value of boolean is false (empty).
        this.seatStatus = new boolean[screen.getTotalSeats() + 1];
    }

    public static Screen createStandardScreen(int id, int seats) {
        return new StandardScreen(id, seats);
    }

    public static Screen createIMAXScreen(int id, int seats) {
        return new IMAXScreen(id, seats);
    }

    // Accept primitive int array
    public synchronized Booking bookSeats(User user, int[] seats) 
            throws BookingConflictException, InvalidSeatException {
        
        // 1. Validation Loop
        for (int i = 0; i < seats.length; i++) {
            int seatNumber = seats[i];

            // Check bounds
            if (seatNumber < 1 || seatNumber > screen.getTotalSeats()) {
                throw new InvalidSeatException("Seat " + seatNumber + " does not exist.");
            }
            
            // Check availability (O(1) lookup in array)
            if (seatStatus[seatNumber]) {
                throw new BookingConflictException("Seat " + seatNumber + " is already booked!");
            }
        }

        // 2. Booking Loop (Update boolean array)
        for (int i = 0; i < seats.length; i++) {
            int seatNumber = seats[i];
            seatStatus[seatNumber] = true;
        }

        // 3. Calculate Cost
        double totalCost = basePrice * screen.getPriceMultiplier() * seats.length;

        return new Booking(user, movie.getTitle(), seats, totalCost);
    }
}