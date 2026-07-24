package thor.core.port.mapping.dto;

import thor.usefulUtils.utils.dataStructures.Point;

public record ChestInfoDto(
        Point position,
        Integer size,
        Integer quality,
        Double probability,
        String material,
        String fillType
) {
}
