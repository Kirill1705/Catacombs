package thor.catacombs.info.structure.interfaces;

import thor.catacombs.info.structure.TunnelType;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public interface PartTunnelInfo extends StructureInfo, PlayerSpawnableStructureInfo, Nameable {
    BlockPosition getAttachmentPoint();
    TunnelType getType();
}
