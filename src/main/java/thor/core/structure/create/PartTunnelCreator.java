package thor.core.structure.create;

import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.part.PartTunnelInfo;
import thor.core.structure.PartTunnel;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public interface PartTunnelCreator {
    PartTunnel create(Point position, Point offset, PartTunnelInfo info, Converter converter);
}
