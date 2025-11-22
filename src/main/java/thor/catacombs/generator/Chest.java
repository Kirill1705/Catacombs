package thor.catacombs.generator;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import thor.catacombs.generator.items.Item;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.info.block.ChestInfo;

public class Chest {
    final BlockPosition position;
    final ChestInfo info;
    private final Item[] items;
    public Chest(BlockPosition position, ChestInfo info, RandomGenerator<Item> generator) {
        this.info=info;
        this.position=position.add(info.getPosition());
        items = new Item[info.getSize()];
        for (int i = 0; i < info.getSize(); i++) {
            items[i]=generator.getRandom(info.getQuality());
        }
    }
    public void feelOrDeleteBlock(BlockLocation location) {
        Block block = location.add(position).toLocation().getBlock();
        if (!info.canPlace()) {
            block.setType(Material.AIR);
            return;
        }
        if (block.getType()!=info.getMaterial()) {
            block.setType(info.getMaterial());
        }
        Container container = (Container)block.getState();
        Inventory inventory = container.getInventory();
        for (Item item: items) {
            inventory.addItem(item.getItemStack().toItemStack());
        }
    }
}
