package thor.core.info.part;

import ru.vikhrenko.serverUtils.utils.dataStructures.DoublePosition;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.exception.DomainValidationException;
import thor.core.info.SignalType;

public record TeleportInfo(Point trigger, Point place, Point direction, SignalType signalType) {
    public TeleportInfo {
        if (!trigger.moreOrEquals(new Point(0, 0, 0))) {
            throw new DomainValidationException(trigger);
        }
        if (!place.moreOrEquals(new Point(0, 0, 0))) {
            throw new DomainValidationException(trigger);
        }
        if (direction.abs().sumXYZ() != 1) {
            throw new DomainValidationException(direction);
        }
    }
}
