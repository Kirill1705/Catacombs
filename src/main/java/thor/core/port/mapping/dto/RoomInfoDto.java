package thor.core.port.mapping.dto;

import thor.usefulUtils.utils.dataStructures.Point;

import java.util.List;

public record RoomInfoDto(
        String id,
        Integer weight,
        Point size,
        List<ChestInfoDto> chests,
        List<PlayerSpawnPlaceInfoDto> playerSpawnPlaces,
        List<ExitInfoDto> exits,
        List<String> tunnels
){}
