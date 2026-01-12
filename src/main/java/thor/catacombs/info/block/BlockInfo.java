package thor.catacombs.info.block;

import thor.usefulUtils.utils.dataStructures.BlockPosition;

public class BlockInfo {
    public BlockPosition getPosition() {
        return position;
    }
    private final BlockPosition position;
    public BlockInfo(BlockPosition position) {
        if (position.x() < 0 || position.y() < 0 || position.z() < 0) {
            throw new RuntimeException("local BlockInfo position should be greater then zero!");
        }
        this.position = position;
    }
}
