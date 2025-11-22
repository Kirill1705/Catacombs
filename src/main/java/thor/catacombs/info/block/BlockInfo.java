package thor.catacombs.info.block;

import thor.usefulUtils.utils.dataStructures.BlockPosition;

public class BlockInfo {
    public BlockPosition getPosition() {
        return position;
    }
    private final BlockPosition position;
    public BlockInfo(BlockPosition position) {
        this.position = position;
    }
}
