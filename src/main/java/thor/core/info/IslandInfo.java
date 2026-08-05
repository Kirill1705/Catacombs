package thor.core.info;

import lombok.Getter;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.info.part.*;

import java.util.Collection;

public class IslandInfo extends StructureInfo {
    @Getter
    private final TeleportInfo teleport;
    @Getter
    private final Collection<EffectInfo> effects;
    @Getter
    private final Collection<ArenaButtonInfo> arenaButtons;
    @Getter
    private final Collection<PortalInfo> portals;

    public IslandInfo(Weight weight, String textId, Collection<PlayerSpawnPlaceInfo> playerSpawnPlaces, Point size, Collection<ChestInfo> chests, TeleportInfo teleport, Collection<EffectInfo> effects, Collection<ArenaButtonInfo> arenaButtons, Collection<PortalInfo> portals) {
        super(size, weight, textId, chests, playerSpawnPlaces);
        this.teleport = teleport;
        this.effects = effects;
        this.arenaButtons = arenaButtons;
        this.portals = portals;
    }
}
