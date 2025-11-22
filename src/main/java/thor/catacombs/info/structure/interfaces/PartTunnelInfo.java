package thor.catacombs.info.structure.interfaces;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.info.structure.TunnelType;

public interface PartTunnelInfo extends StructureInfo, PlayerSpawnableStructureInfo, Nameable {
    BlockPosition getAttachmentPoint();
    TunnelType getType();
}
