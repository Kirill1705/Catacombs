package thor.core.port.mapping.dto;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.List;

public record ExitInfoDto(
        Point position,
        String material,
        List<Point> blocks
) {
}
