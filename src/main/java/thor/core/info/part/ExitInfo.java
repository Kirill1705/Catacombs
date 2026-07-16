package thor.core.info.part;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import thor.core.exception.DomainValidationException;
import thor.core.util.ConfUtils;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ExitInfo {
    @Getter
    private final BlockPosition position;
    @Getter
    private final Material material;
    private final Collection<BlockPosition> blocks;

    public ExitInfo(Material material, BlockPosition position, Collection<BlockPosition> blocks) {
        if (position.notMore(new Point(0, 0, 0)))
            throw new DomainValidationException(position);
        this.blocks = ConfUtils.takeOrDefault(blocks, List.of());
        this.position = position;
        this.material = ConfUtils.takeOrDefault(material, Material.AIR);
    }

    public BlockPosition getOffset(BlockPosition roomSize) {
        return new Point(predicateToOffset(position.x() == 0, position.x() == roomSize.x()-1), predicateToOffset(position.y() == 0, position.y() == roomSize.y()-1), predicateToOffset(position.z() == 0, position.z() == roomSize.z()-1));
    }

    public ExitType getType(BlockPosition roomSize) {
        BlockPosition offset = getOffset(roomSize);
        if (Math.abs(offset.sumXYZ()) != 1) {
            throw new DomainValidationException(offset);
        }
        if (offset.y() == -1 || offset.y() == 1) {
            return ExitType.VERTICAL;
        } else {
            return ExitType.HORIZONTAL;
        }
    }

    private int predicateToOffset(boolean b1, boolean b2) {
        if (b1) return -1;
        if (b2) return 1;
        return 0;
    }

    private static boolean isExitMaterial(Material material) {
        return Tag.DOORS.isTagged(material) || Tag.TRAPDOORS.isTagged(material) || material == Material.LADDER;
    }

    public static boolean isCollidable(Block block) {
        return block.isSolid() && !block.isPassable() && !isExitMaterial(block.getType());
    }

    public Collection<BlockPosition> getBlocks() {
        return Collections.unmodifiableCollection(blocks);
    }

    public enum ExitType {
        VERTICAL,
        HORIZONTAL
    }
}
