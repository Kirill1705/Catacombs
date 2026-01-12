package thor.catacombs.info.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.InventoryHolder;
import thor.catacombs.info.attributes.AttributeHolderType;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.attributes.AttributeType;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public class ChestInfo extends BlockInfo{
    public static final AttributeHolderType TYPE = AttributeHolderType.CHEST;
    public int getSize() {
        return size;
    }

    public int getQuality() {
        return quality;
    }
    public FeelType getType() {
        return type;
    }

    private final FeelType type;
    private final double probability;
    private final int size;
    private final int quality;
    public Material getMaterial() {
        return material;
    }
    private final Material material;
    public ChestInfo(YamlConfiguration configuration, BlockPosition position, Block block, AttributeRegistry registry) {
        super(position);
        this.material = block.getType();
        probability = registry.getAttribute(TYPE, AttributeType.PROBABILITY, configuration);
        int rawSize = registry.getAttribute(TYPE, AttributeType.SIZE, configuration);
        if (rawSize<1||rawSize>10) throw new RuntimeException("size "+rawSize+" must be from 1 to 10");
        int maxSize;
        if (block.getState() instanceof InventoryHolder holder) {
            maxSize = holder.getInventory().getSize();
        }
        else {
            throw new RuntimeException("This is not container!");
        }
        size = (int) (rawSize/10D* maxSize);
        quality = registry.getAttribute(TYPE, AttributeType.QUALITY, configuration);
        if (registry.containsValue(TYPE, AttributeType.FILL_TYPE, configuration)) {
            type = FeelType.valueOf(registry.<String>getAttribute(TYPE, AttributeType.FILL_TYPE, configuration).toUpperCase());
        }
        else {
            type = getDefaultType(block);
        }
    }
    private FeelType getDefaultType(Block block) {
        if (block.getType()==Material.BARREL) {
            return FeelType.BARREL;
        }
        return FeelType.CHEST;
    }
    public boolean canPlace() {
        return Math.random()<probability;
    }
}
