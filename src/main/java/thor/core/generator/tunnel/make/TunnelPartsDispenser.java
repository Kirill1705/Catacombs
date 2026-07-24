package thor.core.generator.tunnel.make;

import thor.usefulUtils.utils.dataStructures.Point;

public interface TunnelPartsDispenser {
    TunnelProgress getProgress(Point size, Point position);
}
