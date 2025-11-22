package thor.catacombs.generator.map.bind;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

public class Transformer {
    private final BlockPosition begin;
    private final BlockPosition matrix;
    public Transformer(BlockPosition begin, BlockPosition end) {
        this.begin = begin;
        matrix = new Point((int)Math.signum(end.x()-begin.x()), (int)Math.signum(end.y()-begin.y()), (int)Math.signum(end.z()-begin.z()));
    }
    public BlockPosition convertVector(BlockPosition old) {
        return old.multiply(matrix);
    }
    public BlockPosition toNew(BlockPosition old) {
        return old.subtract(begin).multiply(matrix);
    }
    public BlockPosition toOld(BlockPosition position) {
        return position.multiply(matrix).add(begin);
    }
}
