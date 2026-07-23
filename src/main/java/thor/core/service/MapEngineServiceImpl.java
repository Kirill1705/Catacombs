package thor.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import thor.core.generator.complete.GameMap;
import thor.core.port.input.LocationDto;
import thor.core.port.input.MapPlaceOptions;
import thor.core.port.input.MapEngineService;
import thor.core.port.mapping.PositionMapper;
import thor.core.port.mapping.dto.PositionDto;
import thor.core.port.mapping.dto.map.PlacedMapDto;
import thor.core.port.mapping.dto.map.PlacedMapMapper;
import thor.core.port.output.ArenaManager;
import thor.core.port.output.WorldAccessor;
import thor.core.port.output.repository.MapRepository;
import thor.core.port.output.repository.PlacedMapRepository;
import thor.core.world.MapPlacer;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class MapEngineServiceImpl implements MapEngineService {
    private final MapRepository mapRepository;
    private final WorldAccessor accessor;
    private final ArenaManager arenaManager;
    private final PlacedMapRepository placedMapRepository;

    @Override
    public PlacedMapDto placeMap(LocationDto location, UUID mapId, MapPlaceOptions options) {
        Optional<GameMap> gameMap = mapRepository.findById(mapId);
        if (gameMap.isEmpty()) {
            throw new IllegalArgumentException();
        }
        MapPlacer mapPlacer = new MapPlacer(gameMap.get(), accessor);
        mapPlacer.place(location, options.isFillBedrock(), options.isFillStone());
        arenaManager.placeArena(getArenaPosition(fromDto(location), gameMap.get().getField().getSize()), location.worldName());
        PlacedMapDto dto = PlacedMapMapper.toDto(gameMap.get(), fromDto(location), location.worldName(), options.getSpawnPlacesCount());
        placedMapRepository.addMapOrReplace(dto);
        log.info("Map placed successfully");
        return dto;
    }

    @Override
    public void tptoArena(UUID playerId, UUID placedMapId) {
        Optional<PlacedMapDto> dto = placedMapRepository.findById(placedMapId);
        if (dto.isEmpty()) {
            return;
        }
        arenaManager.tpPlayerToArena(playerId, dto.get().corner2(), dto.get().worldName());
    }

    @Override
    public boolean tpFromArena(UUID playerId) {
        return arenaManager.tpPlayerFromArena(playerId);
    }

    private BlockPosition fromDto(LocationDto locationDto) {
        return new Point(locationDto.x(), locationDto.y(), locationDto.z());
    }

    private PositionDto getArenaPosition(BlockPosition mapPosition, BlockPosition size) {
        return PositionMapper.toPositionDto(mapPosition.add(size).subtract(new Point(1, 1, 1)));
    }
}
