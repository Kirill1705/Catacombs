package thor.core.port.mapping.dto.map;

import thor.core.generator.complete.GameMap;
import thor.core.port.mapping.StructuresMapper;
import thor.core.structure.PartTunnel;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.*;

public class PlacedMapMapper {
    public static PlacedMapDto toDto(GameMap map, Point mapPosition, String worldName, int spawnPlacesCount) {
        List<PlayerSpawnPlaceDto> playerSpawnPlaces = new ArrayList<>(map.getPlayerSpawnPlaces().stream().map(node -> StructuresMapper.toDto(node, mapPosition)).toList());
        Collections.shuffle(playerSpawnPlaces);
        if (playerSpawnPlaces.size() < spawnPlacesCount) {
            List<PartTunnel> tunnels = new ArrayList<>(map.getGraph().getAllTunnels());
            Collections.shuffle(tunnels);
            playerSpawnPlaces.addAll(tunnels.stream()
                    .map(partTunnel -> StructuresMapper.toDto(partTunnel, mapPosition))
                    .limit(spawnPlacesCount - playerSpawnPlaces.size())
                    .toList());
        }
        playerSpawnPlaces.sort(Comparator.comparingInt(PlayerSpawnPlaceDto::priority).reversed());
        return new PlacedMapDto(
                UUID.randomUUID(),
                worldName,
                mapPosition,
                mapPosition.add(map.getField().getSize()).subtract(new Point(1, 1, 1)),
                playerSpawnPlaces
        );
    }
}
