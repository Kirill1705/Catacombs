package thor.core.exception;

public class EmptyListForGeneratorException extends RuntimeException {
    public EmptyListForGeneratorException() {
        super("Cant use random generator because the list of elements is empty");
    }
}
