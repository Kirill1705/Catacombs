package thor.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.vikhrenko.serverUtils.utils.dataStructures.Boxes;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;
import thor.core.exception.MapNotFoundException;
import thor.core.exception.MapNotPlacedException;
import thor.core.info.SignalType;
import thor.core.port.input.MapEngineService;
import thor.core.port.mapping.MapPlaceOptions;
import thor.core.port.mapping.dto.MapPartInfo;
import thor.core.port.mapping.dto.map.InteractiveGameMap;
import thor.core.port.output.WorldAccessor;
import thor.core.port.output.WorldAccessorCreator;
import thor.core.port.output.repository.MapRepository;
import thor.core.port.output.repository.MapGeoIndex;
import thor.core.structure.PlacePartResult;
import thor.core.structure.manager.PlacePartManager;
import thor.core.structure.manager.PlayerSpawnManager;
import thor.core.structure.manager.SignalPartManager;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class MapEngineServiceImpl implements MapEngineService {
    private final MapRepository mapRepository;
    private final WorldAccessorCreator accessorCreator;
    private final MapGeoIndex mapGeoIndex;

    @Override
    public void placeMap(Point point, String worldName, UUID mapId, MapPlaceOptions options) {
        Optional<InteractiveGameMap> gameMap = mapRepository.findById(mapId);
        if (gameMap.isEmpty()) {
            throw new IllegalArgumentException();
        }
        for (PlacePartManager manager: gameMap.get().placePartManagers()) {
            Optional<PlacePartResult> result = manager.place(accessorCreator, worldName, point, options);
            if (result.isPresent()) {
                ImmutableBox box = Boxes.fromCorners(point.add(result.get().box().begin()), point.add(result.get().box().end()));
                mapGeoIndex.addToIndex(mapId, box, worldName);
            }
        }
    }

    @Override
    public void tptoArena(UUID playerId, UUID placedMapId) {
        Optional<InteractiveGameMap> mapOptional = mapRepository.findById(placedMapId);
        if (mapOptional.isEmpty()) {
            return;
        }
        if (mapOptional.get().arenaTeleportator() == null) {
            throw new IllegalStateException("Game map does not have arena. Cant teleport entity");
        }
        mapOptional.get().arenaTeleportator().teleport(playerId);
    }

    @Override
    public void onPressedSomething(UUID playerId, Point position, String worldName, String signalType) {
        SignalType type = SignalType.valueOf(signalType.toUpperCase());
        Optional<MapPartInfo> mapId = mapGeoIndex.findByLocation(position, worldName);
        if (mapId.isEmpty()) {
            return;
        }
        Optional<InteractiveGameMap> mapOptional = mapRepository.findById(mapId.get().mapId());
        if (mapOptional.isEmpty()) return;
        WorldAccessor accessor = accessorCreator.create(mapId.get().position(), worldName);
        for (SignalPartManager manager: mapOptional.get().signalPartManagers()) {
            manager.onSignal(accessor, playerId, position.subtract(mapId.get().position()), type);
        }
    }

    @Override
    public void tpPlayers(List<UUID> entityIds, UUID placedMapId) {
        Optional<InteractiveGameMap> mapOptional = mapRepository.findById(placedMapId);
        if (mapOptional.isEmpty()) {
            throw new MapNotFoundException();
        }

        mapOptional.get().playerSpawnManager().tpPlayers(entityIds);
    }

}
