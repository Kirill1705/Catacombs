package thor.catacombs.generator.map.bind;

import thor.usefulUtils.utils.dataStructures.BlockPosition;

public interface PlaceChecker {
    boolean canPlace(BlockPosition position, boolean isVertical);
}
