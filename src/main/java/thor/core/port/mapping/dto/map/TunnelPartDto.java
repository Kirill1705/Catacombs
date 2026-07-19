package thor.core.port.mapping.dto.map;

import thor.core.port.mapping.dto.PositionDto;

public record TunnelPartDto(
        PositionDto position,
        PositionDto size,
        PositionDto attachmentPoint
) {
}
