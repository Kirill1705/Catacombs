package thor.core.port.mapping.dto;

import java.util.List;

public record PartTunnelDescriptionDto(
        List<Integer> size,
        List<Integer> attachmentPoint,
        List<ChestInfoDto> chests,
        List<PlayerSpawnPlaceInfoDto> playerSpawnPlaces
        ){
}
