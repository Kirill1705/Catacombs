package thor.core.port.mapping.dto.map;

import thor.core.generator.complete.GameMap;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.part.PlayerSpawnPlaceInfo;
import thor.core.port.mapping.StructuresMapper;
import thor.core.structure.PartTunnel;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.structure.PlayerSpawnNode;
import thor.core.structure.PlayerSpawnPriority;

import java.util.*;

public class PlacedMapMapper {
    public static PlacedMapDto toDto(GameMap map, Point mapPosition, String worldName, int spawnPlacesCount) {
        List<PlayerSpawnNode> playerSpawnPlaces = new ArrayList<>(map.getPlayerSpawnPlaces());
        Collections.shuffle(playerSpawnPlaces);
        if (playerSpawnPlaces.size() < spawnPlacesCount) {
            List<PartTunnel> tunnels = new ArrayList<>(map.getGraph().getAllTunnels());
            Collections.shuffle(tunnels);
            playerSpawnPlaces.addAll(tunnels.stream()
                    .map(PlacedMapMapper::getTunnelSpawn)
                    .limit(spawnPlacesCount - playerSpawnPlaces.size())
                    .toList());
        }
        playerSpawnPlaces.sort(Comparator.comparing(PlayerSpawnNode::getPriority));
        return new PlacedMapDto(
                UUID.randomUUID(),
                worldName,
                mapPosition,
                mapPosition.add(map.getField().getSize()).subtract(new Point(1, 1, 1)),
                playerSpawnPlaces.stream().map(node -> StructuresMapper.toDto(node, mapPosition)).toList()
        );
    }

    private static PlayerSpawnNode getTunnelSpawn(PartTunnel partTunnel) {
        return new PlayerSpawnNode(Converter.simple(new Point(0, 0, 0)), new PlayerSpawnPlaceInfo(
                PlayerSpawnPriority.lowest(),
                partTunnel.getAttachmentPoint()
        ));
    }
}
