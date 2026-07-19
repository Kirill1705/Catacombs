package thor.core.port.mapping;

import thor.core.port.mapping.dto.PositionDto;
import thor.core.port.mapping.dto.map.PlayerSpawnPlaceDto;
import thor.core.port.mapping.dto.map.RoomDto;
import thor.core.port.mapping.dto.map.TunnelPartDto;
import thor.core.structure.PartTunnel;
import thor.core.structure.PlayerSpawnNode;
import thor.core.structure.Room;
import thor.core.structure.RoomImpl;

public class StructuresMapper {
    public static RoomDto toDto(Room room) {
        return new RoomDto(
                PositionMapper.toPositionDto(room.getPosition()),
                PositionMapper.toPositionDto(room.getSize())
        );
    }

    public static TunnelPartDto toDto(PartTunnel partTunnel) {
        return new TunnelPartDto(
                PositionMapper.toPositionDto(partTunnel.getPosition()),
                PositionMapper.toPositionDto(partTunnel.getSize()),
                PositionMapper.toPositionDto(partTunnel.getAttachmentPoint())
        );
    }

    public static PlayerSpawnPlaceDto toDto(PlayerSpawnNode node) {
        return new PlayerSpawnPlaceDto(
                PositionMapper.toPositionDto(node.getPosition()),
                node.getPriority().getValue()
        );
    }
}
