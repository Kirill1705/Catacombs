package thor.core.exception;

public class MissingItemsForFillTypeException extends RuntimeException {
    public MissingItemsForFillTypeException(String fillType) {
        super("No items found for fill type " + fillType);
    }
}
