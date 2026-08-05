package thor.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.vikhrenko.serverUtils.utils.dataStructures.*;
import thor.core.exception.MapNotFoundException;
import thor.core.generator.complete.GeneratedGameMap;
import thor.core.info.SignalType;
import thor.core.port.input.MapEngineService;
import thor.core.port.mapping.MapPlaceOptions;
import thor.core.port.mapping.dto.MapPartInfo;
import thor.core.port.mapping.dto.map.InteractiveGameMap;
import thor.core.port.output.WorldAccessor;
import thor.core.port.output.repository.MapPlacedRepository;
import thor.core.port.output.repository.MapRepository;
import thor.core.structure.create.CatacombsInteractiveMapCreator;
import thor.core.structure.create.InteractiveGameMapWithBoxes;
import thor.core.structure.manager.SignalPartManager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class MapEngineServiceImpl implements MapEngineService {
    private final MapRepository mapRepository;
    private final MapPlacedRepository mapPlacedRepository;
    private final CatacombsInteractiveMapCreator creator;
    private final WorldAccessor accessor;

    @Override
    public void placeMap(Point position, String worldName, UUID mapId, MapPlaceOptions options) {
        Optional<GeneratedGameMap> gameMap = mapRepository.findById(mapId);
        if (gameMap.isEmpty()) {
            throw new MapNotFoundException();
        }
        InteractiveGameMapWithBoxes mapWithBoxes = creator.create(gameMap.get().maps(), new ImmutableLocation(position, worldName), accessor, options);
        UUID id = mapPlacedRepository.addMap(mapWithBoxes.map());
        for (ImmutableWorldBox box: mapWithBoxes.boxes()) {
            mapPlacedRepository.addToIndex(id, box.box(), box.worldName());
        }
    }

    @Override
    public void tptoArena(UUID playerId, UUID placedMapId) {
        Optional<InteractiveGameMap> mapOptional = mapPlacedRepository.findById(placedMapId);
        if (mapOptional.isEmpty()) {
            return;
        }
        if (mapOptional.get().arenaTeleportator() == null) {
            throw new IllegalStateException("Game map does not have arena. Cant teleport entity");
        }
        mapOptional.get().arenaTeleportator().teleport(playerId, accessor);
    }

    @Override
    public void onPressedSomething(UUID playerId, Point position, String worldName, String signalType) {
        SignalType type = SignalType.valueOf(signalType.toUpperCase());
        Optional<MapPartInfo> mapId = mapPlacedRepository.findByLocation(position, worldName);
        if (mapId.isEmpty()) {
            return;
        }
        for (SignalPartManager manager: mapId.get().map().signalPartManagers()) {
            manager.onSignal(accessor, playerId, position, type, worldName);
        }
    }

    @Override
    public void tpPlayers(List<UUID> entityIds, UUID placedMapId) {
        Optional<InteractiveGameMap> mapOptional = mapPlacedRepository.findById(placedMapId);
        if (mapOptional.isEmpty()) {
            throw new MapNotFoundException();
        }

        mapOptional.get().playerSpawnManager().tpPlayers(entityIds, accessor);
    }

    @Override
    public boolean onTeleportingToAnotherWorld(UUID entityId, Point position, String sourceWorld, String destWorld) {
        Optional<MapPartInfo> map = mapPlacedRepository.findByLocation(position, sourceWorld);
        if (map.isEmpty()) {
            return false;
        }
        if (map.get().map().portalHandler() != null) {
            try {
                map.get().map().portalHandler().tryTeleport(accessor, entityId, position, sourceWorld, destWorld);
            } catch (RuntimeException e) {
                log.error("Error while trying handle portal event", e);
            }
            return true;
        }
        return false;
    }
}
