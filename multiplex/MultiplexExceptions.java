package multiplex;

public class MultiplexExceptions {
    // Exception for when a seat is already taken
    public static class BookingConflictException extends Exception {
        public BookingConflictException(String message) { super(message); }
    }

    // Exception for invalid seat numbers (e.g., seat -1)
    public static class InvalidSeatException extends Exception {
        public InvalidSeatException(String message) { super(message); }
    }
}