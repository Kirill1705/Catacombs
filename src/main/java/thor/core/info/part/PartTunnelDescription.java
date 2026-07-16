package thor.core.info.part;

import thor.usefulUtils.utils.dataStructures.BlockPosition;

import java.util.Collection;

public record PartTunnelDescription(BlockPosition size, BlockPosition attachmentPoint, Collection<ChestInfo> chests, Collection<PlayerSpawnPlaceInfo> playerSpawnPlaces) {
}
