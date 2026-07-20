package thor.core.port.mapping.dto.map;

import thor.core.port.mapping.dto.PositionDto;

import java.util.List;
import java.util.UUID;

public record PlacedMapDto(
        UUID id,
        String worldName,
        PositionDto corner1,
        PositionDto corner2,
        List<PlayerSpawnPlaceDto> playerSpawnPlaces
) {
}
