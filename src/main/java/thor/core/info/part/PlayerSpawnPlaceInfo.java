package thor.core.info.part;

import lombok.Getter;
import thor.core.structure.PlayerSpawnPriority;
import thor.usefulUtils.utils.dataStructures.Point;

public class PlayerSpawnPlaceInfo {
    @Getter
    private final PlayerSpawnPriority priority;
    @Getter
    private final Point position;

    public PlayerSpawnPlaceInfo(PlayerSpawnPriority priority, Point position) {
        this.priority = priority;
        this.position = position;
    }
}
