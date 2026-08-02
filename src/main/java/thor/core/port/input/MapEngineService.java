package thor.core.port.input;

import thor.core.port.mapping.MapPlaceOptions;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.List;
import java.util.UUID;

public interface MapEngineService {
    /**
     * @param options map place options
     * @param mapId   UUID of generated map
     */
    void placeMap(Point point, String worldName, UUID mapId, MapPlaceOptions options);

    void tptoArena(UUID playerId, UUID placedMapId);

    void onPressedSomething(UUID playerId, Point position, String worldName, String signalType);

    void tpPlayers(List<UUID> entityIds, UUID placedMapId);
}
