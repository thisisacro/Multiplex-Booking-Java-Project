import multiplex.*; 
import java.util.Scanner;

public class Main {
    
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        Movie avatar = new Movie("Avatar 2", "Sci-Fi");
        Screen imax = Show.createIMAXScreen(1, 10); 
        Show eveningShow = new Show(avatar, imax, 20.0); 

        System.out.println("=== MULTIPLEX BOOKING SYSTEM (PRIMITIVE VERSION) ===");
        
        while (true) {
            System.out.println("\n1. Book Tickets");
            System.out.println("2. Run Concurrency Test");
            System.out.println("3. Exit");
            System.out.print("Enter Choice: ");

            // Helper to safely read int choice
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // Ignore invalid input
            }

            if (choice == 1) {
                runInteractiveMode(eveningShow);
            } else if (choice == 2) {
                runConcurrencyTest(eveningShow);
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static void runInteractiveMode(Show show) {
        System.out.println("\n--- NEW BOOKING ---");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        User user = new User(name);

        System.out.print("Enter seats (e.g. 1,2): ");
        String line = scanner.nextLine();

        try {
            // Manual String parsing to int[]
            String[] parts = line.split(",");
            int[] seats = new int[parts.length];
            
            for (int i = 0; i < parts.length; i++) {
                seats[i] = Integer.parseInt(parts[i].trim());
            }

            Booking booking = show.bookSeats(user, seats);
            System.out.println("SUCCESS: " + booking);

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format.");
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
        }
    }

    private static void runConcurrencyTest(Show show) {
        System.out.println("\n--- CONCURRENCY TEST ---");
        
        User u1 = new User("Alice");
        User u2 = new User("Bob");

        // Alice wants 5, 6
        final int[] seatsAlice = {5, 6};
        // Bob wants 6, 7 (Conflict on 6)
        final int[] seatsBob = {6, 7};

        Runnable task1 = () -> {
            try {
                System.out.println("Alice requesting...");
                show.bookSeats(u1, seatsAlice);
                System.out.println("Alice SUCCESS");
            } catch (Exception e) {
                System.out.println("Alice FAILED: " + e.getMessage());
            }
        };

        Runnable task2 = () -> {
            try {
                System.out.println("Bob requesting...");
                show.bookSeats(u2, seatsBob);
                System.out.println("Bob SUCCESS");
            } catch (Exception e) {
                System.out.println("Bob FAILED: " + e.getMessage());
            }
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);
        t1.start();
        t2.start();
        
        try { t1.join(); t2.join(); } catch (InterruptedException e) {}
    }
}