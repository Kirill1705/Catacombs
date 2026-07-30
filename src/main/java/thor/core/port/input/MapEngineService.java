package thor.core.port.input;

import thor.core.port.mapping.MapPlaceOptions;
import thor.core.port.mapping.dto.map.PlacedMapDto;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.UUID;

public interface MapEngineService {
    /**
     * @param options map place options
     * @param mapId UUID of generated map
     * @return map view
     */
    PlacedMapDto placeMap(Point point, String worldName, UUID mapId, MapPlaceOptions options);

    void tptoArena(UUID playerId, UUID placedMapId);

    boolean tpFromArena(UUID playerId);
}
