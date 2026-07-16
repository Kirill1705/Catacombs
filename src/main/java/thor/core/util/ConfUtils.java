package thor.core.util;

import thor.core.exception.DomainValidationException;

import java.util.function.Function;
import java.util.function.Predicate;

public class ConfUtils {
    public static <T> T takeOrDefault(T value, T defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public static <T> T checkOrDefault(T value, T defaultValue, Predicate<T> constraint) {
        T result = takeOrDefault(value, defaultValue);
        if (!constraint.test(result)) {
            throw new DomainValidationException(value);
        }
        return result;
    }

    public static <E> E enumOrNull(String value, Function<String, E> valueOf) {
        if (value == null) {
            return null;
        }
        return valueOf.apply(value.toUpperCase());
    }
}
