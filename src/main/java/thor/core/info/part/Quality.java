package thor.core.info.part;

import lombok.Getter;
import thor.core.util.ConfUtils;

public class Quality {
    @Getter
    private final int value;

    public Quality(Integer value) {
        final int default_value = 5;
        this.value = ConfUtils.checkOrDefault(value, default_value, integer -> integer >= 1 && integer <= 10);
    }
}
