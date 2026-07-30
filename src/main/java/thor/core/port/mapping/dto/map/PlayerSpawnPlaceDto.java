package thor.core.port.mapping.dto.map;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public record PlayerSpawnPlaceDto(
        Point positionDto,
        int priority
) {
}
