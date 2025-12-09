package exceptions;

public class MaxSlotsReached extends RuntimeException {
    public MaxSlotsReached(String message) {
        super(message);
    }
}
