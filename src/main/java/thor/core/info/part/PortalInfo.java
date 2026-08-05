package thor.core.info.part;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.List;

public record PortalInfo(List<Point> portals, Point backPosition, Dimension dimension) {
}
