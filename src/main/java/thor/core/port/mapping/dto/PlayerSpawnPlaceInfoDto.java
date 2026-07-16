package thor.core.port.mapping.dto;

import java.util.List;

public record PlayerSpawnPlaceInfoDto(
        List<Integer> position,
        Integer priority
) {
}
