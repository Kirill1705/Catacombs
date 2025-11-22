package thor.catacombs.generator.map.bind;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

public class DirectionCalculator {
    private final BlockPosition size;
    private final BlockPosition direction;
    public DirectionCalculator(BlockPosition size) {
        this.size = size;
        direction = size.x()<size.z() ? new Point(0, 0, 1) : new Point(1, 0, 0);
    }

    public BlockPosition getSize() {
        return size;
    }

    public BlockPosition getDirection() {
        return direction;
    }
}
