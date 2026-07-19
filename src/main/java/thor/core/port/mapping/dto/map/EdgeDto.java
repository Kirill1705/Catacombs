package thor.core.port.mapping.dto.map;

import java.util.List;

public record EdgeDto(
        RoomDto first,
        RoomDto second,
        List<TunnelPartDto> tunnel
) {
}
