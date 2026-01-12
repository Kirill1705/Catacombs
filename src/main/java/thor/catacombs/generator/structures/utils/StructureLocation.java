package thor.catacombs.generator.structures.utils;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.OffsetBox;

public class StructureLocation {
    private final OffsetBox box;

    public StructureLocation(OffsetBox box) {
        this.box = box;
    }

    public BlockPosition convert(BlockPosition position) {
        BlockPosition result = box.begin().add(position);
        if (!box.contains(result)) {
            throw new RuntimeException("This position is not in structure!");
        }

        return result;
    }

    public BlockPosition begin() {
        return box.begin();
    }
}
