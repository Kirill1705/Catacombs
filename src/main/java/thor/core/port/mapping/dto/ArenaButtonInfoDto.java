package thor.core.port.mapping.dto;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public record ArenaButtonInfoDto(Point position, Point backPosition, String signalType) {
}
