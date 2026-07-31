package thor.core.port.mapping.dto.map;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.structure.Teleport;
import thor.core.structure.manager.TeleportManager;

import java.util.List;
import java.util.UUID;

public record PlacedMapDto(
        UUID id,
        String worldName,
        Point corner1,
        Point corner2,
        List<PlayerSpawnPlaceDto> playerSpawnPlaces,
        TeleportManager teleportManager
) {
}
