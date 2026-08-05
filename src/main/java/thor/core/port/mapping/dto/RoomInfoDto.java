package thor.core.port.mapping.dto;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.info.part.ArenaButtonInfo;
import thor.core.info.part.TeleportInfo;

import java.util.List;

public record RoomInfoDto(
        String id,
        Integer weight,
        Point size,
        List<ChestInfoDto> chests,
        List<PlayerSpawnPlaceInfoDto> playerSpawnPlaces,
        List<ExitInfoDto> exits,
        List<String> tunnels,
        TeleportInfoDto teleport,
        List<EffectInfoDto> effects,
        List<ArenaButtonInfoDto> arenaButtons,
        List<PortalInfoDto> portals
){
    public RoomInfoDto(String id, Integer weight, Point size, List<ChestInfoDto> chests, List<PlayerSpawnPlaceInfoDto> playerSpawnPlaces, List<ExitInfoDto> exits, List<String> tunnels) {
        this(id, weight, size, chests, playerSpawnPlaces, exits, tunnels, null, List.of(), List.of(), List.of());
    }
}
