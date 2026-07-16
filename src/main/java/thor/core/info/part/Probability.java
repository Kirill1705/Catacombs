package thor.core.info.part;

import lombok.Getter;
import thor.core.util.ConfUtils;

public class Probability {
    @Getter
    private final double value;

    public Probability(Double value) {
        final double defaultValue = 1.0;
        this.value = ConfUtils.checkOrDefault(value, defaultValue, realValue -> realValue >= 0 && realValue <= 1);
    }
}
