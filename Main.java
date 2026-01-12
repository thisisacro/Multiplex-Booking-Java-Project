import multiplex.*; 
import java.util.Scanner;

public class Main {
    
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        Movie avatar = new Movie("Avatar 2", "Sci-Fi");

        // 1. Create TWO screens
        Screen standardScreen = Show.createStandardScreen(1, 10);
        Screen imaxScreen = Show.createIMAXScreen(2, 10); 

        // 2. Create TWO shows (Same movie, different screens)
        // Base price is $20.0 for both, but multipliers will apply automatically
        Show standardShow = new Show(avatar, standardScreen, 20.0); 
        Show imaxShow = new Show(avatar, imaxScreen, 20.0); 

        System.out.println("=== MULTIPLEX BOOKING SYSTEM ===");
        
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Book Tickets");
            System.out.println("2. Run Concurrency Test (IMAX only)");
            System.out.println("3. Exit");
            System.out.print("Enter Choice: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // Ignore invalid input
            }

            if (choice == 1) {
                // 3. Ask user to choose screen type
                System.out.println("\nSelect Screen Type:");
                System.out.println("1. Standard Screen (1.0x Price)");
                System.out.println("2. IMAX Screen     (1.5x Price)");
                System.out.print("Enter Option: ");
                
                try {
                    int screenChoice = Integer.parseInt(scanner.nextLine());
                    
                    if (screenChoice == 1) {
                        runInteractiveMode(standardShow, "Standard");
                    } else if (screenChoice == 2) {
                        runInteractiveMode(imaxShow, "IMAX");
                    } else {
                        System.out.println("Invalid screen selection.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }

            } else if (choice == 2) {
                runConcurrencyTest(imaxShow); // Test runs on IMAX by default
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    // Updated to accept screen name for better display
    private static void runInteractiveMode(Show show, String screenType) {
        System.out.println("\n--- NEW BOOKING (" + screenType + ") ---");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        User user = new User(name);

        System.out.print("Enter seats (e.g. 1,2): ");
        String line = scanner.nextLine();

        try {
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

        final int[] seatsAlice = {5, 6};
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