package thor.core.port.mapping;

import thor.core.port.mapping.dto.map.PlayerSpawnPlaceDto;
import thor.core.structure.PartTunnel;
import thor.core.structure.PlayerSpawnNode;
import thor.core.structure.PlayerSpawnPriority;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public class StructuresMapper {
    public static PlayerSpawnPlaceDto toDto(PlayerSpawnNode node, BlockPosition mapPosition) {
        return new PlayerSpawnPlaceDto(
                PositionMapper.toPositionDto(node.getPosition().add(mapPosition)),
                node.getPriority().getValue()
        );
    }

    public static PlayerSpawnPlaceDto toDto(PartTunnel partTunnel, BlockPosition mapPosition) {
        return new PlayerSpawnPlaceDto(
                PositionMapper.toPositionDto(partTunnel.getAttachmentPoint().add(mapPosition)),
                PlayerSpawnPriority.lowest().getValue()
        );
    }
}
