package thor.core.port.mapping.dto;

import thor.usefulUtils.utils.dataStructures.Point;

import java.util.List;

public record PartTunnelDescriptionDto(
        Point size,
        Point attachmentPoint,
        List<ChestInfoDto> chests,
        List<PlayerSpawnPlaceInfoDto> playerSpawnPlaces
        ){
}
