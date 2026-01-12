package thor.catacombs.generator.structures.utils;

import org.bukkit.block.Block;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.ImmutableBox;

public class GameWorldAccessor {
    private final ImmutableBox box;

    public GameWorldAccessor(ImmutableBox box) {
        this.box = box;
    }

    public Block getBlockAt(StructureLocation structureLocation, BlockPosition position) {
        return convert(structureLocation, position).toLocation().getBlock();
    }

    public BlockLocation convert(StructureLocation structureLocation, BlockPosition position) {
        BlockLocation result = box.begin().add(structureLocation.convert(position));
        if (!box.contains(result))
            throw new RuntimeException("This location is over game map!");
        return result;
    }

    public BlockLocation begin(StructureLocation structureLocation) {
        return box.begin().add(structureLocation.begin());
    }

    public BlockLocation begin() {
        return box.begin();
    }
}
