package thor.catacombs.info.block;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import thor.catacombs.info.attributes.AttributeHolderType;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.attributes.AttributeType;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.Collection;
import java.util.Collections;

public class ExitInfo extends BlockInfo{
    public static final AttributeHolderType TYPE = AttributeHolderType.EXIT;
    public enum ExitType {
        VERTICAL,
        HORIZONTAL
    }
    private final ExitType type;

    public ExitType getType() {
        return type;
    }
    public BlockPosition getOffset() {
        return offset;
    }

    public Material getMaterial() {
        return material;
    }
    public Collection<BlockPosition> getBlocks() {
        return Collections.unmodifiableCollection(blocks);
    }
    private final Point offset;
    private final Collection<BlockPosition> blocks;
    private Material material;
    private int booleanToInteger(boolean b1, boolean b2) {
        if (b1) return -1;
        if (b2) return 1;
        return 0;
    }
    public ExitInfo(YamlConfiguration config, Material material, BlockPosition position, BlockPosition size, AttributeRegistry registry, Collection<BlockPosition> blocks) {
        super(position);
        this.blocks = blocks;
        this.material = material;
        if (registry.containsValue(TYPE, AttributeType.MATERIAL, config)) {
            this.material = Material.valueOf(registry.<String>getAttribute(TYPE, AttributeType.MATERIAL, config).toUpperCase());
        }
        offset = new Point(booleanToInteger(position.x() == 0, position.x() == size.x()-1), booleanToInteger(position.y() == 0, position.y() == size.y()-1), booleanToInteger(position.z() == 0, position.z() == size.z()-1));
        if (offset.y() == -1 || offset.y() == 1) {
            type = ExitType.VERTICAL;
        } else {
            type = ExitType.HORIZONTAL;
        }
    }

    private static boolean isExitMaterial(Material material) {
        return Tag.DOORS.isTagged(material) || Tag.TRAPDOORS.isTagged(material) || material == Material.LADDER;
    }

    public static boolean isCollidable(Block block) {
        return block.isSolid() && !block.isPassable() && !isExitMaterial(block.getType());
    }
}