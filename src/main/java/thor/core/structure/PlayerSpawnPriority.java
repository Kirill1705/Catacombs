package thor.core.structure;

import lombok.Getter;
import thor.core.util.ConfUtils;

public class PlayerSpawnPriority {
    public static PlayerSpawnPriority lowest() {
        return new PlayerSpawnPriority(minPriority);
    }

    private static final int minPriority = 100;

    @Getter
    private final int value;

    public PlayerSpawnPriority(Integer value) {
        final int defaultValue = 0;
        this.value = ConfUtils.checkOrDefault(value, defaultValue, integer -> integer >= 0 && integer <= minPriority);
    }

    public boolean morePriorityThen(PlayerSpawnPriority other) {
        return value < other.value;
    }
}
