package thor.core.port.mapping.dto.map;

import thor.core.generator.complete.GameMap;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.part.PlayerSpawnPlaceInfo;
import thor.core.port.mapping.StructuresMapper;
import thor.core.structure.PartTunnel;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.structure.PlayerSpawnNode;
import thor.core.structure.PlayerSpawnPriority;
import thor.core.structure.create.StructurePartsHolder;
import thor.core.structure.manager.PlayerSpawnManager;
import thor.core.structure.manager.SignalPartManagerGroup;

import java.util.*;

public class PlacedMapMapper {
    public static AllMapInfo toDto(GameMap map, Point mapPosition, String worldName, int spawnPlacesCount) {
        StructurePartsHolder partsHolder = map.getPartsHolder();
        PlayerSpawnManager spawnManager = partsHolder.getPlayerSpawnManager();
        spawnManager.addSpawnsBeforeCap(spawnPlacesCount, new ArrayList<>(map.getGraph().getAllTunnels()));
        List<PlayerSpawnNode> playerSpawnPlaces = spawnManager.getSortedNodes();
        return new AllMapInfo(new PlacedMapDto(
                UUID.randomUUID(),
                worldName,
                mapPosition,
                mapPosition.add(map.getField().getSize()).subtract(new Point(1, 1, 1)),
                playerSpawnPlaces.stream().map(node -> StructuresMapper.toDto(node, mapPosition)).toList()
        ), new SignalPartManagerGroup(List.of(partsHolder.getTeleportManager(), partsHolder.getEffectNodeManager())));
    }
}
