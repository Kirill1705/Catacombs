package thor.core.port.mapping;

import thor.core.port.mapping.dto.map.PlayerSpawnPlaceDto;
import thor.core.structure.PartTunnel;
import thor.core.structure.PlayerSpawnNode;
import thor.core.structure.PlayerSpawnPriority;
import thor.usefulUtils.utils.dataStructures.Point;

public class StructuresMapper {
    public static PlayerSpawnPlaceDto toDto(PlayerSpawnNode node, Point mapPosition) {
        return new PlayerSpawnPlaceDto(
                node.getPosition().add(mapPosition),
                node.getPriority().getValue()
        );
    }

    public static PlayerSpawnPlaceDto toDto(PartTunnel partTunnel, Point mapPosition) {
        return new PlayerSpawnPlaceDto(
                partTunnel.getAttachmentPoint().add(mapPosition),
                PlayerSpawnPriority.lowest().getValue()
        );
    }
}
