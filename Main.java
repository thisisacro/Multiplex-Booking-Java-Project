import multiplex.*; 
import java.util.*;

public class Main {
    
    // Global Scanner for reading input
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        // --- 1. SETUP DATA ---
        Movie avatar = new Movie("Avatar 2", "Sci-Fi");
        Screen imax = Show.createIMAXScreen(1, 10); // Small screen (10 seats) for easy testing
        
        // Base price $20.0
        Show eveningShow = new Show(avatar, imax, 20.0); 

        System.out.println("=== MULTIPLEX BOOKING SYSTEM ===");
        
        while (true) {
            System.out.println("\nSelect an Option:");
            System.out.println("1. Book Tickets (Interactive Mode)");
            System.out.println("2. Run Concurrency Stress Test (Simulation)");
            System.out.println("3. Exit");
            System.out.print("Enter Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    runInteractiveMode(eveningShow);
                    break;
                case 2:
                    runConcurrencyTest(eveningShow);
                    break;
                case 3:
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // --- MODE 1: INTERACTIVE USER INPUT ---
    private static void runInteractiveMode(Show show) {
        System.out.println("\n--- NEW BOOKING ---");
        
        // 1. Get User Name
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        User user = new User(name);

        // 2. Get Seats
        System.out.print("Enter seat numbers to book (separated by comma, e.g., 1,2): ");
        String input = scanner.nextLine();
        
        // Convert "1,2" string into List<Integer>
        List<Integer> seats = new ArrayList<>();
        try {
            String[] parts = input.split(",");
            for (String part : parts) {
                seats.add(Integer.parseInt(part.trim()));
            }

            // 3. Attempt Booking
            Booking booking = show.bookSeats(user, seats);
            System.out.println(" BOOKING SUCCESSFUL!");
            System.out.println(booking);

        } catch (NumberFormatException e) {
            System.out.println(" Error: Please enter valid numbers.");
        } catch (Exception e) {
            System.out.println(" BOOKING FAILED: " + e.getMessage());
        }
    }

    // --- MODE 2: CONCURRENCY SIMULATION ---
    private static void runConcurrencyTest(Show show) {
        System.out.println("\n--- RUNNING CONCURRENCY TEST ---");
        System.out.println("Simulating Alice and Bob trying to book Seat #5 at the EXACT same time...");

        User u1 = new User("Alice");
        User u2 = new User("Bob");

        // Both want seat 5
        List<Integer> seatForAlice = Arrays.asList(5, 6);
        List<Integer> seatForBob = Arrays.asList(5, 7); 

        Runnable task1 = () -> {
            try {
                System.out.println("Alice requesting seats: " + seatForAlice);
                show.bookSeats(u1, seatForAlice);
                System.out.println(" Alice got her tickets.");
            } catch (Exception e) {
                System.out.println(" Alice failed: " + e.getMessage());
            }
        };

        Runnable task2 = () -> {
            try {
                System.out.println("Bob requesting seats: " + seatForBob);
                show.bookSeats(u2, seatForBob);
                System.out.println(" Bob got his tickets.");
            } catch (Exception e) {
                System.out.println(" Bob failed: " + e.getMessage());
            }
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();
        
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}