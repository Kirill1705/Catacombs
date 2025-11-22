package thor.catacombs.info.structure;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.ImmutableOffsetBox;
import thor.usefulUtils.utils.dataStructures.OffsetBox;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.ArrayList;
import java.util.List;

public class TunnelDivider {
    private final List<OffsetBox> parts = new ArrayList<>();
    public List<OffsetBox> getParts() {
        return parts;
    }
    public TunnelDivider(BlockPosition size, TunnelType type) {
        for (int i = 0; i < size.scalarMultiply(type.getZeroMask()); i++) {
            int x2 = type==TunnelType.X ? i : size.x()-1;
            int y2 = type==TunnelType.VERTICAL ? i : size.y()-1;
            int z2 = type==TunnelType.Z ? i : size.z()-1;
            int x1 = type==TunnelType.X ? i : 0;
            int y1 = type==TunnelType.VERTICAL ? i : 0;
            int z1 = type==TunnelType.Z ? i : 0;
            parts.add(new ImmutableOffsetBox(new Point(x1, y1, z1), new Point(x2, y2, z2)));
        }
    }
}
