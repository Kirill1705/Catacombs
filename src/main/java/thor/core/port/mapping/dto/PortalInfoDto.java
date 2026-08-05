package thor.core.port.mapping.dto;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.List;

public record PortalInfoDto(List<Point> blocks, Point backPosition, String dimension) {
}
