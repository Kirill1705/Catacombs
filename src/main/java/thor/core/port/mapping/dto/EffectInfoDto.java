package thor.core.port.mapping.dto;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public record EffectInfoDto(Point position, String effect, int amplifier, int duration, String signalType) {
}
