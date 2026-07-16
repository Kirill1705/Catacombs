package thor.core.info.part;

import thor.core.exception.DomainValidationException;

public record Weight(int value) {
    public Weight {
        if (value <= 0)
            throw new DomainValidationException(value);
    }
}
