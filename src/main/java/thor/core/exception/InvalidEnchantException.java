package thor.core.exception;

public class InvalidEnchantException extends RuntimeException {
    public InvalidEnchantException(String enchant) {
        super("Enchant " + enchant + " does not exists");
    }
}
