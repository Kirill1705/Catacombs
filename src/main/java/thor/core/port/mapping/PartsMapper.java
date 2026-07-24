package thor.core.port.mapping;

import org.bukkit.Material;
import thor.core.info.part.*;
import thor.core.port.mapping.dto.ChestInfoDto;
import thor.core.port.mapping.dto.ExitInfoDto;
import thor.core.port.mapping.dto.PlayerSpawnPlaceInfoDto;
import thor.core.structure.PlayerSpawnPriority;
import thor.core.util.ConfUtils;

import java.util.ArrayList;

public final class PartsMapper {
    public static ExitInfo fromDto(ExitInfoDto dto) {
        return new ExitInfo(
                ConfUtils.enumOrNull(dto.material(), Material::valueOf),
                dto.position(),
                dto.blocks() != null ? dto.blocks() : null
        );
    }

    public static ExitInfoDto toDto(ExitInfo domain) {
        return new ExitInfoDto(
                domain.getPosition(),
                domain.getMaterial().name().toLowerCase(),
                new ArrayList<>(domain.getBlocks())
        );
    }

    public static ChestInfo fromDto(ChestInfoDto dto) {
        return new ChestInfo(
                new ChestSize(dto.size()),
                new Quality(dto.quality()),
                new Probability(dto.probability()),
                ConfUtils.enumOrNull(dto.material(), Material::valueOf),
                ConfUtils.enumOrNull(dto.fillType(), FillType::valueOf),
                dto.position()
        );
    }

    public static ChestInfoDto toDto(ChestInfo domain) {
        return new ChestInfoDto(
                domain.getPosition(),
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
                dto.position()
        );
    }

    public static PlayerSpawnPlaceInfoDto toDto(PlayerSpawnPlaceInfo domain) {
        return new PlayerSpawnPlaceInfoDto(
                domain.getPosition(),
                domain.getPriority().getValue()
        );
    }
}
