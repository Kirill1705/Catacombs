package thor.catacombs.generator.map.bind;

import thor.usefulUtils.utils.dataStructures.BlockPosition;

import java.util.Collection;

public interface Neighbors {
    Collection<BlockPosition> getNeighbors(BlockPosition current);
}