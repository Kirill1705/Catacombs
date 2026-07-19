package thor.core.port.mapping.dto.map;

import java.util.List;
import java.util.UUID;

public record MapDto(
        UUID id,
        List<EdgeDto> map,
        List<PlayerSpawnPlaceDto> playerSpawnPlaces
) {
}
