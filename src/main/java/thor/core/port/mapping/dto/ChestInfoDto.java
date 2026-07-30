package thor.core.port.mapping.dto;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public record ChestInfoDto(
        Point position,
        Integer size,
        Integer quality,
        Double probability,
        String material,
        String fillType
) {
}
