package thor.core.port.mapping.dto;

import java.util.List;

public record RoomInfoDto(
        String id,
        Integer weight,
        List<Integer> size,
        List<ChestInfoDto> chests,
        List<PlayerSpawnPlaceInfoDto> playerSpawnPlaces,
        List<ExitInfoDto> exits,
        List<String> tunnels
){}
