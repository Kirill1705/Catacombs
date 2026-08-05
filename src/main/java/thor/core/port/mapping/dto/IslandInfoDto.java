package thor.core.port.mapping.dto;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.List;

public record IslandInfoDto(
        String id,
        Integer weight,
        Point size,
        List<ChestInfoDto> chests,
        List<PlayerSpawnPlaceInfoDto> playerSpawnPlaces,
        TeleportInfoDto teleport,
        List<EffectInfoDto> effects,
        List<ArenaButtonInfoDto> arenaButtons,
        List<PortalInfoDto> portals
) {}
