package thor.catacombs.generator.map.bind;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.ImmutableOffsetBox;
import thor.usefulUtils.utils.dataStructures.OffsetBox;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NeighborsImpl implements Neighbors {
    private final BlockPosition size;
    private final BlockPosition direction;
    private final OffsetBox box;
    private final boolean beginOffset;
    private final boolean endOffset;
    public NeighborsImpl(DirectionCalculator calculator, BlockPosition beginOffset, BlockPosition endOffset) {
        this.size = calculator.getSize();
        box = new ImmutableOffsetBox(new Point(0, 0, 0), size);
        direction = calculator.getDirection();
        this.beginOffset = beginOffset.abs().equals(new Point(0, 1, 0));
        this.endOffset = endOffset.abs().equals(new Point(0, 1, 0));
    }

    @Override
    public Collection<BlockPosition> getNeighbors(BlockPosition current) {
        BlockPosition disabled = new Point(1, 0, 1).subtract(direction);
        BlockPosition[] offsets = {
                new Point(1, 1, 1),
                new Point(1, 0 ,0),
                new Point(0, 0, 1),
                new Point(1, 0, 1)
        };
        List<BlockPosition> result = new ArrayList<>();
        for (BlockPosition offset: offsets) {
            BlockPosition candidate = current.add(offset);
            if (!offset.equals(disabled) && box.contains(candidate)) {
                result.add(candidate);
            }
        }
        if (current.x()==0 && current.z()==0 && beginOffset || current.x()==size.x() && current.z()==size.z() && endOffset) {
            BlockPosition candidate = current.add(new Point(0, 1, 0));
            if (box.contains(candidate)) {
                result.add(candidate);
            }
        }
        return result;
    }
}
