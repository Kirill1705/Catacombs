package thor.core.info.part;

import lombok.Getter;
import thor.core.structure.PlayerSpawnPriority;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public class PlayerSpawnPlaceInfo {
    @Getter
    private final PlayerSpawnPriority priority;
    @Getter
    private final BlockPosition position;

    public PlayerSpawnPlaceInfo(PlayerSpawnPriority priority, BlockPosition position) {
        this.priority = priority;
        this.position = position;
    }
}
