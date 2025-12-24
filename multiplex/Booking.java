package multiplex;
import java.util.List;

public class Booking {
    private User user;
    private String movieTitle;
    private List<Integer> seats;
    private double cost;

    public Booking(User user, String movieTitle, List<Integer> seats, double cost) {
        this.user = user;
        this.movieTitle = movieTitle;
        this.seats = seats;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Receipt: [User: " + user.getName() + ", Movie: " + movieTitle + 
               ", Seats: " + seats + ", Cost: $" + cost + "]";
    }
}