package thor.catacombs.info.structure;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

public enum TunnelType {
    X(new Point(0, 1, 1), new Point(1, 0, 0)),
    Z(new Point(1, 1, 0), new Point(0, 0, 1)),
    VERTICAL(new Point(1, 0, 1), new Point(0, 1, 0));

    public BlockPosition getZeroMask() {
        return zeroMask;
    }

    public BlockPosition getOneMask() {
        return oneMask;
    }

    private final BlockPosition zeroMask;
    private final BlockPosition oneMask;

    TunnelType(BlockPosition oneMask, BlockPosition zeroMask) {
        this.oneMask = oneMask;
        this.zeroMask = zeroMask;
    }
}
