package thor.core.port.mapping.dto.map;

import thor.core.port.mapping.dto.PositionDto;

public record PlayerSpawnPlaceDto(
        PositionDto positionDto,
        int priority
) {
}
