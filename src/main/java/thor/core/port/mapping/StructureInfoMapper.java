package thor.core.port.mapping;

import thor.core.info.IslandInfo;
import thor.core.info.RoomInfo;
import thor.core.info.TunnelInfo;
import thor.core.info.part.*;
import thor.core.port.mapping.dto.*;
import thor.core.util.ConfUtils;

import java.util.List;

public final class StructureInfoMapper {
    public static PartTunnelDescription fromDto(PartTunnelDescriptionDto dto) {
        return new PartTunnelDescription(
                dto.size(),
                dto.attachmentPoint(),
                dto.chests() != null ? dto.chests().stream().map(PartsMapper::fromDto).toList() : List.of(),
                dto.playerSpawnPlaces() != null ? dto.playerSpawnPlaces().stream().map(PartsMapper::fromDto).toList() : List.of()
        );
    }

    public static PartTunnelDescriptionDto toDto(PartTunnelInfo domain) {
        return new PartTunnelDescriptionDto(
                domain.getSize(),
                domain.getAttachmentPoint(),
                domain.getChests().stream().map(PartsMapper::toDto).toList(),
                domain.getPlayerSpawnPlaces().stream().map(PartsMapper::toDto).toList()
        );
    }

    public static TunnelInfo fromDto(TunnelInfoDto dto) {
        return new TunnelInfo(
                dto.size(),
                new Weight(dto.weight()),
                dto.id(),
                ConfUtils.enumOrNull(dto.type(), TunnelType::valueOf),
                dto.parts().stream().map(StructureInfoMapper::fromDto).toList(),
                dto.neutral()
        );
    }

    public static TunnelInfoDto toDto(TunnelInfo domain) {
        return new TunnelInfoDto(
                domain.getSize(),
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
                dto.size(),
                dto.chests() != null ? dto.chests().stream().map(PartsMapper::fromDto).toList() : List.of(),
                dto.tunnels(),
                PartsMapper.fromDto(dto.teleport()),
                dto.effects() != null ? dto.effects().stream().map(PartsMapper::fromDto).toList() : List.of(),
                dto.arenaButtons() != null ? dto.arenaButtons().stream().map(PartsMapper::fromDto).toList() : List.of(),
                dto.portals() != null ? dto.portals().stream().map(PartsMapper::fromDto).toList() : List.of()
        );
    }

    public static RoomInfoDto toDto(RoomInfo domain) {
        return new RoomInfoDto(
                domain.getTextId(),
                domain.getWeight().value(),
                domain.getSize(),
                domain.getChests().stream().map(PartsMapper::toDto).toList(),
                domain.getPlayerSpawnPlaces().stream().map(PartsMapper::toDto).toList(),
                domain.getExits().stream().map(PartsMapper::toDto).toList(),
                domain.getTunnels().stream().toList(),
                PartsMapper.toDto(domain.getTeleport()),
                domain.getEffects().stream().map(PartsMapper::toDto).toList(),
                domain.getArenaButtons().stream().map(PartsMapper::toDto).toList(),
                domain.getPortals().stream().map(PartsMapper::toDto).toList()
        );
    }

    public static IslandInfoDto toDto(IslandInfo domain) {
        return new IslandInfoDto(
                domain.getTextId(),
                domain.getWeight().value(),
                domain.getSize(),
                domain.getChests().stream().map(PartsMapper::toDto).toList(),
                domain.getPlayerSpawnPlaces().stream().map(PartsMapper::toDto).toList(),
                PartsMapper.toDto(domain.getTeleport()),
                domain.getEffects().stream().map(PartsMapper::toDto).toList(),
                domain.getArenaButtons().stream().map(PartsMapper::toDto).toList(),
                domain.getPortals().stream().map(PartsMapper::toDto).toList()
        );
    }

    public static IslandInfo fromDto(IslandInfoDto dto) {
        return new IslandInfo(
                new Weight(dto.weight()),
                dto.id(),
                dto.playerSpawnPlaces() != null ? dto.playerSpawnPlaces().stream().map(PartsMapper::fromDto).toList() : List.of(),
                dto.size(),
                dto.chests() != null ? dto.chests().stream().map(PartsMapper::fromDto).toList() : List.of(),
                PartsMapper.fromDto(dto.teleport()),
                dto.effects() != null ? dto.effects().stream().map(PartsMapper::fromDto).toList() : List.of(),
                dto.arenaButtons() != null ? dto.arenaButtons().stream().map(PartsMapper::fromDto).toList() : List.of(),
                dto.portals() != null ? dto.portals().stream().map(PartsMapper::fromDto).toList() : List.of()
        );
    }
}
