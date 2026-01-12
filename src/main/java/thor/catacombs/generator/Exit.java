package thor.catacombs.generator;

import thor.catacombs.generator.structures.utils.GameWorldAccessor;
import thor.catacombs.generator.structures.utils.StructureLocation;
import thor.catacombs.info.block.ExitInfo;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public class Exit {
    private final ExitInfo info;
    private final StructureLocation position;

    public boolean isClosed() {
        return closed;
    }
    public ExitInfo getInfo() {
        return info;
    }
    public BlockPosition getPosition() {
        return position.convert(info.getPosition());
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
    boolean closed = false;
    public Exit(ExitInfo info, StructureLocation roomPosition) {
        this.info=info;
        position = roomPosition;
    }

    public void place(GameWorldAccessor accessor) {
        if (!isClosed()) {
            for (BlockPosition position: info.getBlocks()) {
                accessor.getBlockAt(this.position, position).setType(info.getMaterial());
            }
        }
    }
}
