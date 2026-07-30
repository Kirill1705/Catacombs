package thor.core.port.output;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public interface WorldAccessorCreator {
    WorldAccessor create(Point position, String worldName);
}
