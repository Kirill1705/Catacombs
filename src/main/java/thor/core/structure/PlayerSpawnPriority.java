package thor.core.structure;

import lombok.Getter;
import thor.core.util.ConfUtils;

public class PlayerSpawnPriority {
    @Getter
    private final int value;

    public PlayerSpawnPriority(Integer value) {
        final int defaultValue = 0;
        this.value = ConfUtils.checkOrDefault(value, defaultValue, integer -> integer >= 0);
    }

    public boolean morePriorityThen(PlayerSpawnPriority other) {
        return value < other.value;
    }
}
