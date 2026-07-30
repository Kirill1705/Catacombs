package thor.core.generator.tunnel.make;

import thor.core.exception.DomainValidationException;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public class TunnelPartsDispenserImpl implements TunnelPartsDispenser {
    private final int startPartSize;
    private final int endPartSize;

    public TunnelPartsDispenserImpl(int startPartSize, int endPartSize) {
        if (startPartSize <= 0) {
            throw new IllegalArgumentException();
        }
        if (endPartSize <= 0) {
            throw new IllegalArgumentException();
        }
        this.startPartSize = startPartSize;
        this.endPartSize = endPartSize;
    }

    @Override
    public TunnelProgress getProgress(Point size, Point position, Integer idx) {
        int distance = maxCoord(size);
        if (idx != null && idx < distance/2) {
            if (idx < startPartSize) {
                return TunnelProgress.START;
            }
            else {
                return TunnelProgress.NEUTRAL;
            }
        }
        Point diff = size.subtract(position);
        int remainDistance = maxCoord(diff);
        if (remainDistance < endPartSize) {
            return TunnelProgress.END;
        }
        return TunnelProgress.NEUTRAL;
    }

    private int maxCoord(Point point) {
        return Math.max(point.x(), Math.max(point.y(), point.z()));
    }
}
