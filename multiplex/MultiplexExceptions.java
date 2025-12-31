package multiplex;

public class MultiplexExceptions {
    public static class BookingConflictException extends Exception {
        public BookingConflictException(String message) { super(message); }
    }

    public static class InvalidSeatException extends Exception {
        public InvalidSeatException(String message) { super(message); }
    }
}