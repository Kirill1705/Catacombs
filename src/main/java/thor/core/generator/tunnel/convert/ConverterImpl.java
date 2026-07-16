package thor.core.generator.tunnel.convert;

import lombok.Getter;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

public class ConverterImpl implements Converter{
    @Getter
    private final BlockPosition begin;
    private final BlockPosition matrix;
    public ConverterImpl(BlockPosition begin, BlockPosition end) {
        this.begin = begin;
        matrix = new Point(signum(end.x()-begin.x()), signum(end.y()-begin.y()), signum(end.z()-begin.z()));
    }

    private int signum(int number) {
        int signum = (int) Math.signum(number);
        if (signum == 0) {
            return 1;
        }
        return signum;
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
