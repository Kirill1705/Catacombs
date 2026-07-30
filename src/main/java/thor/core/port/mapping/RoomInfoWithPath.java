package thor.core.port.mapping;

import thor.core.port.mapping.dto.RoomInfoDto;

public record RoomInfoWithPath(RoomInfoDto roomInfo, String structure) {
}
