package thor.core.structure;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public interface PartTunnel extends Structure, AfterPlacing {
    boolean isVertical();
    Point getAttachmentPoint();
}
