import multiplex.*; // Import our package
import java.util.*;

public class Main {
    public static void main(String[] args) {
        
        // 1. Setup Logic
        Movie avatar = new Movie("Avatar 2", "Sci-Fi");
        Screen imax = Show.createIMAXScreen(1, 50); // Using helper from Show class
        
        // Create a show with $20 base price
        Show eveningShow = new Show(avatar, imax, 20.0); 

        // 2. Define Users
        User alice = new User("Alice");
        User bob = new User("Bob");

        // 3. Multithreading Simulation
        // Scenario: Alice and Bob both try to book seats 5 and 6 at the same time.
        
        Runnable aliceTask = () -> {
            List<Integer> seats = Arrays.asList(5, 6);
            System.out.println("Alice is trying to book seats: " + seats);
            try {
                Booking b = eveningShow.bookSeats(alice, seats);
                System.out.println("SUCCESS for Alice: " + b);
            } catch (Exception e) {
                System.out.println("FAILURE for Alice: " + e.getMessage());
            }
        };

        Runnable bobTask = () -> {
            List<Integer> seats = Arrays.asList(6, 7); // Conflict on seat 6!
            System.out.println("Bob is trying to book seats: " + seats);
            try {
                Booking b = eveningShow.bookSeats(bob, seats);
                System.out.println("SUCCESS for Bob: " + b);
            } catch (Exception e) {
                System.out.println("FAILURE for Bob: " + e.getMessage());
            }
        };

        // 4. Run threads
        Thread t1 = new Thread(aliceTask);
        Thread t2 = new Thread(bobTask);

        t1.start();
        t2.start();
    }
}