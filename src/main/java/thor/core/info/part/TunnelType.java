package thor.core.info.part;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

public enum TunnelType {
    HORIZONTAL,
    VERTICAL;
    public static TunnelType fromOffset(BlockPosition offset) {
        if (offset.abs().equals(new Point(0, 1, 0))) {
            return TunnelType.VERTICAL;
        }
        else if (offset.abs().equals(new Point(1, 0, 0)) || offset.abs().equals(new Point(0, 0, 1))) {
            return TunnelType.HORIZONTAL;
        }
        throw new RuntimeException("This is not offset!" + offset);
    }
}
