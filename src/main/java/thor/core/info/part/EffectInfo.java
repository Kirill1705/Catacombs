package thor.core.info.part;

import org.bukkit.potion.PotionEffectType;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.exception.DomainValidationException;
import thor.core.info.SignalType;

public record EffectInfo(Point position, String effect, int amplifier, int duration, SignalType signalType) {
    public EffectInfo {
        if (!position.moreOrEquals(new Point(0, 0, 0))) {
            throw new DomainValidationException(position);
        }
        if (amplifier < 0) throw new DomainValidationException(amplifier);
        if (duration <= 0) throw new DomainValidationException(duration);
    }
}
