package thor.core.port.mapping.dto;

import thor.usefulUtils.utils.dataStructures.Point;

public record PlayerSpawnPlaceInfoDto(
        Point position,
        Integer priority
) {
}
