package thor.core.info.part;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public enum TunnelType {
    HORIZONTAL,
    VERTICAL;
    public static TunnelType fromOffset(Point offset) {
        if (offset.abs().equals(new Point(0, 1, 0))) {
            return TunnelType.VERTICAL;
        }
        else if (offset.abs().equals(new Point(1, 0, 0)) || offset.abs().equals(new Point(0, 0, 1))) {
            return TunnelType.HORIZONTAL;
        }
        throw new RuntimeException("This is not offset!" + offset);
    }
}
