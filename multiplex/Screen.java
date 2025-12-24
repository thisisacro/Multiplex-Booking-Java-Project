package multiplex;

// Base Abstract Class
public abstract class Screen {
    protected int screenId;
    protected int totalSeats;

    public Screen(int screenId, int totalSeats) {
        this.screenId = screenId;
        this.totalSeats = totalSeats;
    }

    public int getTotalSeats() { return totalSeats; }
    public abstract double getPriceMultiplier();
}

// Subclass 1: Standard Screen
class StandardScreen extends Screen {
    public StandardScreen(int id, int seats) { super(id, seats); }
    public double getPriceMultiplier() { return 1.0; }
}

// Subclass 2: IMAX Screen
class IMAXScreen extends Screen {
    public IMAXScreen(int id, int seats) { super(id, seats); }
    public double getPriceMultiplier() { return 1.5; } // 50% more expensive
}