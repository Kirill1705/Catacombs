package thor.core.generator.tunnel.make;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.Optional;

public interface TunnelPartsDispenser {
    TunnelProgress getProgress(Point size, Point position, Integer idx);
}
