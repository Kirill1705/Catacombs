package thor.core.port.mapping.dto;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.UUID;

public record MapPartInfo(UUID mapId, Point position) {
}
