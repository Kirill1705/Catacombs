package thor.core.exception;

import java.util.UUID;

public class MapNotFoundException extends RuntimeException {
    public MapNotFoundException() {
        super("Map not found");
    }
}
