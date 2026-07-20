package thor.core.port.mapping.dto.map;

import thor.core.generator.complete.GameMap;
import thor.core.port.mapping.PositionMapper;
import thor.core.port.mapping.StructuresMapper;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class PlacedMapMapper {
    public static PlacedMapDto toDto(GameMap map, BlockPosition mapPosition, String worldName, int spawnPlacesCount) {
        List<PlayerSpawnPlaceDto> playerSpawnPlaces = new ArrayList<>(map.getPlayerSpawnPlaces().stream().map(node -> StructuresMapper.toDto(node, mapPosition)).toList());
        if (playerSpawnPlaces.size() < spawnPlacesCount) {
            playerSpawnPlaces.addAll(map.getGraph().getAllTunnels().stream()
                    .map(partTunnel -> StructuresMapper.toDto(partTunnel, mapPosition))
                    .limit(spawnPlacesCount - playerSpawnPlaces.size())
                    .toList());
        }
        playerSpawnPlaces.sort(Comparator.comparingInt(PlayerSpawnPlaceDto::priority).reversed());
        return new PlacedMapDto(
                UUID.randomUUID(),
                worldName,
                PositionMapper.toPositionDto(mapPosition),
                PositionMapper.toPositionDto(mapPosition.add(map.getField().getSize()).subtract(new Point(1, 1, 1))),
                playerSpawnPlaces
        );
    }
}
