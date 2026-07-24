package thor.core.exception;

public class RoomsNotFoundException extends RuntimeException {
    public RoomsNotFoundException() {
        super("No rooms found for room generator");
    }
}
