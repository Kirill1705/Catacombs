package thor.core.exception;

public class InvalidMaterialException extends RuntimeException {
    public InvalidMaterialException(String material, String reason) {
        String message = "material " + material + " is invalid. ";
        super(reason == null ? message : message + reason);
    }
}
