package thor.core.exception;

public class MapNotPlacedException extends RuntimeException {
    public MapNotPlacedException() {
        super("Map not placed in world. Cant perform action");
    }
}
