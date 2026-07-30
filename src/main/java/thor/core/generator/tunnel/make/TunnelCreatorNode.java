package thor.core.generator.tunnel.make;

import lombok.Getter;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

@Getter
public final class TunnelCreatorNode {
    private final Point position;
    private final TunnelCreatorNodeStat status;
    private final Point offset;
    private final TunnelCreatorNode parent;
    private final VerticalCount verticalCount;

    public TunnelCreatorNode(Point position, TunnelCreatorNodeStat status, TunnelCreatorNode parent, Point offset) {
        if (offset.abs().sumXYZ() != 1) {
            throw new RuntimeException("This is not offset " + offset);
        }

        this.position = position;
        this.status = status;
        this.parent = parent;
        this.offset = offset;
        this.verticalCount = new VerticalCount(parent, status);
    }

    public TunnelCreatorNode(TunnelCreatorNode parent, Point physicalOffset, TunnelCreatorNodeStat status, Point offset) {
        this(parent.position.add(physicalOffset), status, parent, offset);
    }

    public TunnelCreatorNode(TunnelCreatorNode parent, Point physicalOffset, Point offset) {
        this(parent, physicalOffset, parent.status, offset);
    }

    public static class VerticalCount {
        private final Integer value;

        public VerticalCount(TunnelCreatorNode parent, TunnelCreatorNodeStat status) {
            if (parent == null) {
                value = null;
            }
            else if (parent.getStatus() == TunnelCreatorNodeStat.ROTATION_ENABLED && status == TunnelCreatorNodeStat.VERTICAL){
                value = 0;
            }
            else if (parent.getVerticalCount().value != null && status == TunnelCreatorNodeStat.VERTICAL){
                value = parent.getVerticalCount().value + 1;
            }
            else {
                value = null;
            }
        }

        public boolean canRotate() {
            return value == null || value > 1;
        }
    }
}
