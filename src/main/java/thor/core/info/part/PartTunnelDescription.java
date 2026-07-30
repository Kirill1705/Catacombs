package thor.core.info.part;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.Collection;

public record PartTunnelDescription(Point size, Point attachmentPoint, Collection<ChestInfo> chests, Collection<PlayerSpawnPlaceInfo> playerSpawnPlaces) {
}
