package thor.core.info;

import lombok.Getter;
import thor.core.exception.DomainValidationException;
import thor.core.info.part.ChestInfo;
import thor.core.info.part.ExitInfo;
import thor.core.info.part.PlayerSpawnPlaceInfo;
import thor.core.info.part.Weight;
import thor.core.util.ConfUtils;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RoomInfo extends StructureInfo {
    @Getter
    private final Collection<ExitInfo> exits;
    @Getter
    private final Collection<String> tunnels;

    public RoomInfo(Weight weight, Collection<ExitInfo> exits, String textId, Collection<PlayerSpawnPlaceInfo> playerSpawnPlaces, BlockPosition size, Collection<ChestInfo> chests, Collection<String> tunnels) {
        super(size, weight, textId, chests, playerSpawnPlaces);
        this.tunnels = ConfUtils.takeOrDefault(tunnels, List.of());
        if (exits.isEmpty()) {
            throw new DomainValidationException(exits);
        }
        this.exits = Collections.unmodifiableCollection(exits);
    }
}
