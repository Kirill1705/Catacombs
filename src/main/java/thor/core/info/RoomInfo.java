package thor.core.info;

import lombok.Getter;
import thor.core.exception.DomainValidationException;
import thor.core.info.part.*;
import thor.core.structure.Teleport;
import thor.core.util.ConfUtils;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RoomInfo extends IslandInfo {
    @Getter
    private final Collection<ExitInfo> exits;
    @Getter
    private final Collection<String> tunnels;

    public RoomInfo(Weight weight, Collection<ExitInfo> exits, String textId, Collection<PlayerSpawnPlaceInfo> playerSpawnPlaces, Point size, Collection<ChestInfo> chests, Collection<String> tunnels, TeleportInfo teleport, Collection<EffectInfo> effects, Collection<ArenaButtonInfo> arenaButtons, Collection<PortalInfo> portals) {
        super(weight, textId, playerSpawnPlaces, size, chests, teleport, effects, arenaButtons, portals);
        if (exits.isEmpty()) {
            throw new DomainValidationException(exits);
        }
        this.tunnels = ConfUtils.takeOrDefault(tunnels, List.of());
        this.exits = Collections.unmodifiableCollection(exits);
    }
}
