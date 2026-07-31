package thor.core.port.mapping.dto;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public record TeleportInfoDto(Point trigger, Point place, Point direction, String signalType) {
}
