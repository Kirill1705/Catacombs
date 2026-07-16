package thor.core.port.mapping;

import org.bukkit.Material;
import thor.core.info.part.*;
import thor.core.port.mapping.dto.ChestInfoDto;
import thor.core.port.mapping.dto.ExitInfoDto;
import thor.core.port.mapping.dto.PlayerSpawnPlaceInfoDto;
import thor.core.structure.PlayerSpawnPriority;
import thor.core.util.ConfUtils;

public final class PartsMapper {
    public static ExitInfo fromDto(ExitInfoDto dto) {
        return new ExitInfo(
                ConfUtils.enumOrNull(dto.material(), Material::valueOf),
                PositionMapper.fromDto(dto.position()),
                dto.blocks() != null ? dto.blocks().stream().map(PositionMapper::fromDto).toList() : null
        );
    }

    public static ExitInfoDto toDto(ExitInfo domain) {
        return new ExitInfoDto(
                PositionMapper.toDto(domain.getPosition()),
                domain.getMaterial().name().toLowerCase(),
                domain.getBlocks().stream().map(PositionMapper::toDto).toList()
        );
    }

    public static ChestInfo fromDto(ChestInfoDto dto) {
        return new ChestInfo(
                new ChestSize(dto.size()),
                new Quality(dto.quality()),
                new Probability(dto.probability()),
                ConfUtils.enumOrNull(dto.material(), Material::valueOf),
                ConfUtils.enumOrNull(dto.fillType(), FillType::valueOf),
                PositionMapper.fromDto(dto.position())
        );
    }

    public static ChestInfoDto toDto(ChestInfo domain) {
        return new ChestInfoDto(
                PositionMapper.toDto(domain.getPosition()),
                domain.getSize().getValue(),
                domain.getQuality().getValue(),
                domain.getProbability().getValue(),
                domain.getMaterial().name().toLowerCase(),
                domain.getFillType().name().toLowerCase()
        );
    }

    public static PlayerSpawnPlaceInfo fromDto(PlayerSpawnPlaceInfoDto dto) {
        return new PlayerSpawnPlaceInfo(
                new PlayerSpawnPriority(dto.priority()),
                PositionMapper.fromDto(dto.position())
        );
    }

    public static PlayerSpawnPlaceInfoDto toDto(PlayerSpawnPlaceInfo domain) {
        return new PlayerSpawnPlaceInfoDto(
                PositionMapper.toDto(domain.getPosition()),
                domain.getPriority().getValue()
        );
    }
}
