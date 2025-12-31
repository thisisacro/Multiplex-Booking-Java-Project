package multiplex;

import java.util.Arrays; // Only used for printing array to string

public class Booking {
    private User user;
    private String movieTitle;
    private int[] seats; // PRIMITIVE ARRAY
    private double cost;

    public Booking(User user, String movieTitle, int[] seats, double cost) {
        this.user = user;
        this.movieTitle = movieTitle;
        this.seats = seats;
        this.cost = cost;
    }

    @Override
    public String toString() {
        // Arrays.toString is a helper to print [1, 2] instead of memory address
        return "Receipt: [User: " + user.getName() + ", Movie: " + movieTitle + 
               ", Seats: " + Arrays.toString(seats) + ", Cost: $" + cost + "]";
    }
}