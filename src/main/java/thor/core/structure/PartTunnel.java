package thor.core.structure;

import thor.usefulUtils.utils.dataStructures.BlockPosition;

public interface PartTunnel extends Structure, AfterPlacing {
    boolean isVertical();
    BlockPosition getAttachmentPoint();
}
