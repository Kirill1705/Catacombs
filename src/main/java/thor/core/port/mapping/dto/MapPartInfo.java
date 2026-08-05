package thor.core.port.mapping.dto;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.port.mapping.dto.map.InteractiveGameMap;

import java.util.UUID;

public record MapPartInfo(InteractiveGameMap map, Point position) {
}
