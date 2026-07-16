package thor.core.port.mapping.dto;

import java.util.List;

public record ExitInfoDto(
        List<Integer> position,
        String material,
        List<List<Integer>> blocks
) {
}
