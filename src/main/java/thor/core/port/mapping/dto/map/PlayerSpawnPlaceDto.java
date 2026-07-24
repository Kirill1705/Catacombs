package thor.core.port.mapping.dto.map;

import thor.usefulUtils.utils.dataStructures.Point;

public record PlayerSpawnPlaceDto(
        Point positionDto,
        int priority
) {
}
