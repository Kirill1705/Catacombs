package thor.core.port.mapping.dto;

import java.util.List;

public record ChestInfoDto(
        List<Integer> position,
        Integer size,
        Integer quality,
        Double probability,
        String material,
        String fillType
) {
}
