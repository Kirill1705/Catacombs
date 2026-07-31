package thor.core.port.mapping.dto.map;

import thor.core.generator.complete.GameMap;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.part.PlayerSpawnPlaceInfo;
import thor.core.port.mapping.StructuresMapper;
import thor.core.structure.PartTunnel;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.structure.PlayerSpawnNode;
import thor.core.structure.PlayerSpawnPriority;
import thor.core.structure.manager.PlayerSpawnManager;

import java.util.*;

public class PlacedMapMapper {
    public static PlacedMapDto toDto(GameMap map, Point mapPosition, String worldName, int spawnPlacesCount) {
        PlayerSpawnManager spawnManager = map.getPartsHolder().getPlayerSpawnManager();
        spawnManager.addSpawnsBeforeCap(spawnPlacesCount, new ArrayList<>(map.getGraph().getAllTunnels()));
        List<PlayerSpawnNode> playerSpawnPlaces = spawnManager.getSortedNodes();
        return new PlacedMapDto(
                UUID.randomUUID(),
                worldName,
                mapPosition,
                mapPosition.add(map.getField().getSize()).subtract(new Point(1, 1, 1)),
                playerSpawnPlaces.stream().map(node -> StructuresMapper.toDto(node, mapPosition)).toList(),
                map.getPartsHolder().getTeleportManager()
        );
    }
}
