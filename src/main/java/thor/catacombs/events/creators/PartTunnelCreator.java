package thor.catacombs.events.creators;

import thor.catacombs.generator.structures.TunnelPart;
import thor.catacombs.info.structure.interfaces.PartTunnelInfo;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public interface PartTunnelCreator {
    TunnelPart create(PartTunnelInfo info, BlockPosition position);
}
