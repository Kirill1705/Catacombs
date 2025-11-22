package thor.catacombs.generator.map.bind;

import thor.catacombs.info.structure.TunnelType;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.OffsetBox;

public interface PlaceChecker {
    boolean canPlace(BlockPosition position, boolean isVertical);
}
