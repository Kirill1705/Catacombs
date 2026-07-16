package thor.core.port.mapping;

import thor.core.info.RoomInfo;
import thor.core.info.TunnelInfo;
import thor.core.info.part.PartTunnelDescription;
import thor.core.info.part.PartTunnelInfo;
import thor.core.info.part.TunnelType;
import thor.core.info.part.Weight;
import thor.core.port.mapping.dto.PartTunnelDescriptionDto;
import thor.core.port.mapping.dto.RoomInfoDto;
import thor.core.port.mapping.dto.TunnelInfoDto;
import thor.core.util.ConfUtils;

import java.util.List;

public final class StructureInfoMapper {
    public static PartTunnelDescription fromDto(PartTunnelDescriptionDto dto) {
        return new PartTunnelDescription(
                PositionMapper.fromDto(dto.size()),
                PositionMapper.fromDto(dto.attachmentPoint()),
                dto.chests() != null ? dto.chests().stream().map(PartsMapper::fromDto).toList() : List.of(),
                dto.playerSpawnPlaces() != null ? dto.playerSpawnPlaces().stream().map(PartsMapper::fromDto).toList() : List.of()
        );
    }

    public static PartTunnelDescriptionDto toDto(PartTunnelInfo domain) {
        return new PartTunnelDescriptionDto(
                PositionMapper.toDto(domain.getSize()),
                PositionMapper.toDto(domain.getAttachmentPoint()),
                domain.getChests().stream().map(PartsMapper::toDto).toList(),
                domain.getPlayerSpawnPlaces().stream().map(PartsMapper::toDto).toList()
        );
    }

    public static TunnelInfo fromDto(TunnelInfoDto dto) {
        return new TunnelInfo(
                PositionMapper.fromDto(dto.size()),
                new Weight(dto.weight()),
                dto.id(),
                ConfUtils.enumOrNull(dto.type(), TunnelType::valueOf),
                dto.parts().stream().map(StructureInfoMapper::fromDto).toList(),
                dto.neutral()
        );
    }

    public static TunnelInfoDto toDto(TunnelInfo domain) {
        return new TunnelInfoDto(
                PositionMapper.toDto(domain.getSize()),
                domain.getType().name().toLowerCase(),
                domain.getTextId(),
                domain.getWeight().value(),
                domain.getParts().stream().map(StructureInfoMapper::toDto).toList(),
                domain.isNeutral()
        );
    }

    public static RoomInfo fromDto(RoomInfoDto dto) {
        return new RoomInfo(
                new Weight(dto.weight()),
                dto.exits().stream().map(PartsMapper::fromDto).toList(),
                dto.id(),
                dto.playerSpawnPlaces() != null ? dto.playerSpawnPlaces().stream().map(PartsMapper::fromDto).toList() : List.of(),
                PositionMapper.fromDto(dto.size()),
                dto.chests() != null ? dto.chests().stream().map(PartsMapper::fromDto).toList() : List.of(),
                dto.tunnels()
        );
    }

    public static RoomInfoDto toDto(RoomInfo domain) {
        return new RoomInfoDto(
                domain.getTextId(),
                domain.getWeight().value(),
                PositionMapper.toDto(domain.getSize()),
                domain.getChests().stream().map(PartsMapper::toDto).toList(),
                domain.getPlayerSpawnPlaces().stream().map(PartsMapper::toDto).toList(),
                domain.getExits().stream().map(PartsMapper::toDto).toList(),
                domain.getTunnels().stream().toList()
        );
    }
}
