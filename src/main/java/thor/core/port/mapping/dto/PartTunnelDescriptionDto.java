package thor.core.port.mapping.dto;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.List;

public record PartTunnelDescriptionDto(
        Point size,
        Point attachmentPoint,
        List<ChestInfoDto> chests,
        List<PlayerSpawnPlaceInfoDto> playerSpawnPlaces
        ){
}
