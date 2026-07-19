package thor.core.port.mapping.dto.map;

import thor.core.port.mapping.dto.PositionDto;

import java.util.List;

public record RoomDto(
        PositionDto position,
        PositionDto size
) {
}
