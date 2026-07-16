package thor.core.exception;

public class DomainValidationException extends RuntimeException {
    private final Object cause;
    public DomainValidationException(Object cause) {
        super("The object " + cause + "has invalid value");
        this.cause = cause;
    }

    public Object cause() {
        return cause;
    }
}
