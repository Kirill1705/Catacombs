package thor.core.port.output;

import thor.usefulUtils.utils.dataStructures.Point;

public interface WorldAccessorCreator {
    WorldAccessor create(Point position, String worldName);
}
