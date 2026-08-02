package thor.core.port.mapping;

import org.bukkit.Material;
import thor.core.info.SignalType;
import thor.core.info.part.*;
import thor.core.port.mapping.dto.*;
import thor.core.structure.PlayerSpawnPriority;
import thor.core.util.ConfUtils;

import java.util.ArrayList;
import java.util.List;

public final class PartsMapper {
    public static ExitInfo fromDto(ExitInfoDto dto) {
        return new ExitInfo(
                ConfUtils.enumOrNull(dto.material(), Material::valueOf),
                dto.position(),
                dto.blocks() != null ? dto.blocks() : List.of()
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

    public static TeleportInfoDto toDto(TeleportInfo teleportInfo) {
        if (teleportInfo == null) return null;
        return new TeleportInfoDto(
                teleportInfo.trigger(),
                teleportInfo.place(),
                teleportInfo.direction(),
                teleportInfo.signalType().name().toLowerCase()
        );
    }

    public static TeleportInfo fromDto(TeleportInfoDto dto) {
        if (dto == null) return null;
        return new TeleportInfo(
                dto.trigger(),
                dto.place(),
                dto.direction(),
                SignalType.valueOf(dto.signalType().toUpperCase())
        );
    }

    public static EffectInfoDto toDto(EffectInfo effectInfo) {
        if (effectInfo == null) return null;
        return new EffectInfoDto(
                effectInfo.position(),
                effectInfo.effect(),
                effectInfo.amplifier(),
                effectInfo.duration(),
                effectInfo.signalType().name().toLowerCase()
        );
    }

    public static EffectInfo fromDto(EffectInfoDto dto) {
        if (dto == null) return null;
        return new EffectInfo(
                dto.position(),
                dto.effect(),
                dto.amplifier(),
                dto.duration(),
                SignalType.valueOf(dto.signalType().toUpperCase())
        );
    }

    public static ArenaButtonInfo fromDto(ArenaButtonInfoDto dto) {
        return new ArenaButtonInfo(
                dto.position(),
                dto.backPosition(),
                SignalType.valueOf(dto.signalType().toUpperCase())
        );
    }

    public static ArenaButtonInfoDto toDto(ArenaButtonInfo info) {
        return new ArenaButtonInfoDto(
                info.position(),
                info.backPosition(),
                info.signalType().name().toLowerCase()
        );
    }
}
