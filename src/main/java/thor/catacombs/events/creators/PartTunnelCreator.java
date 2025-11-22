package thor.catacombs.events.creators;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.generator.structures.TunnelPart;
import thor.catacombs.info.structure.interfaces.PartTunnelInfo;

public interface PartTunnelCreator {
    TunnelPart create(PartTunnelInfo info, BlockPosition position);
}
