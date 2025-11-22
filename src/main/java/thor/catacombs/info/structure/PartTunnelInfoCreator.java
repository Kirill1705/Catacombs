package thor.catacombs.info.structure;

import thor.catacombs.info.structure.interfaces.PartTunnelInfo;
import thor.usefulUtils.utils.dataStructures.OffsetBox;

public interface PartTunnelInfoCreator {
    PartTunnelInfo create(OffsetBox attachmentBlockPosition, TunnelType type);
}
