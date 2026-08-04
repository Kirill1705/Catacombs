package thor.core.structure.create;

import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.part.PartTunnelInfo;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.structure.PartTunnel;

public interface PartTunnelCreator {
    PartTunnel create(Point position, Point offset, PartTunnelInfo info, Converter converter);
}
