package thor.catacombs.generator;

import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.info.block.ExitInfo;
import thor.usefulUtils.utils.dataStructures.Point;

public class Exit {
    private final ExitInfo info;
    final BlockPosition position;

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
    public BlockPosition getPosition() {
        return position;
    }

    boolean closed = false;
    public Exit(ExitInfo info, BlockPosition roomPosition) {
        this.info=info;
        position = roomPosition.add(info.getPosition());
    }

    public void place(BlockLocation location) {
        if (!isClosed()) {
            for (BlockPosition position: info.getBlocks()) {
                location.add(position).toLocation().getBlock().setType(info.getMaterial());
            }
        }
    }

    public ExitInfo getInfo() {
        return info;
    }
}
