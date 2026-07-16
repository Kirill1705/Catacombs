package thor.core.structure.create;

import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.part.PartTunnelInfo;
import thor.core.structure.PartTunnel;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public interface PartTunnelCreator {
    PartTunnel create(BlockPosition position, BlockPosition offset, PartTunnelInfo info, Converter converter);
}
