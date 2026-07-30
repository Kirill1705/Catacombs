package thor.core.port.mapping.dto;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public record PlayerSpawnPlaceInfoDto(
        Point position,
        Integer priority
) {
}
