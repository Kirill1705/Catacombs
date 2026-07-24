package thor.core.structure;

import thor.usefulUtils.utils.dataStructures.Point;

public interface PartTunnel extends Structure, AfterPlacing {
    boolean isVertical();
    Point getAttachmentPoint();
}
