package thor.core.port.input;

import thor.core.port.mapping.dto.map.PlacedMapDto;

import java.util.UUID;

public interface MapEngineService {
    /**
     * @param location     location to place map
     * @param options map place options
     * @param mapId UUID of generated map
     * @return map view
     */
    PlacedMapDto placeMap(LocationDto location, UUID mapId, MapPlaceOptions options);

    void tptoArena(UUID playerId, UUID placedMapId);

    boolean tpFromArena(UUID playerId);
}
