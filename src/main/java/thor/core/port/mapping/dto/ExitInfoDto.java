package thor.core.port.mapping.dto;

import thor.usefulUtils.utils.dataStructures.Point;

import java.util.List;

public record ExitInfoDto(
        Point position,
        String material,
        List<Point> blocks
) {
}
