package thor.core.info.part;

import lombok.Getter;
import thor.core.util.ConfUtils;

public class EnchantmentLevel {
    @Getter
    private final int value;

    public EnchantmentLevel(Integer value) {
        int defaultValue = 1;
        this.value = ConfUtils.takeOrDefault(value, defaultValue);
    }
}
