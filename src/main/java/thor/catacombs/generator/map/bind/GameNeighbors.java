package thor.catacombs.generator.map.bind;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record GameNeighbors(Neighbors base, PlaceChecker checker) implements Neighbors {

    @Override
    public Collection<BlockPosition> getNeighbors(BlockPosition current) {
        Collection<BlockPosition> neighbors = base.getNeighbors(current);
        List<BlockPosition> result = new ArrayList<>();
        for (BlockPosition position : neighbors) {
            boolean vertical = position.subtract(current).abs().equals(new Point(0, 1, 0));
            if (checker.canPlace(position, vertical)) {
                result.add(position);
            }
        }
        return result;
    }
}
