package thor.core.info.part;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.info.SignalType;

public record ArenaButtonInfo(Point position, Point backPosition, SignalType signalType) {
}
