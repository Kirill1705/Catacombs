package thor.core.generator.tunnel.convert;

import lombok.Getter;
import thor.usefulUtils.utils.dataStructures.Point;

public class ConverterImpl implements Converter{
    @Getter
    private final Point begin;
    private final Point matrix;
    public ConverterImpl(Point begin, Point end) {
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

    public Point convertVector(Point old) {
        return old.multiply(matrix);
    }

    public Point toNew(Point old) {
        return old.subtract(begin).multiply(matrix);
    }

    public Point toOld(Point position) {
        return position.multiply(matrix).add(begin);
    }
}
