package thor.core.port.mapping.dto.map;

import thor.usefulUtils.utils.dataStructures.Point;

import java.util.List;
import java.util.UUID;

public record PlacedMapDto(
        UUID id,
        String worldName,
        Point corner1,
        Point corner2,
        List<PlayerSpawnPlaceDto> playerSpawnPlaces
) {
}
