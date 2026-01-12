package thor.catacombs.generator;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import thor.catacombs.generator.items.Item;
import thor.catacombs.generator.structures.utils.GameWorldAccessor;
import thor.catacombs.generator.structures.utils.StructureLocation;
import thor.catacombs.info.block.ChestInfo;

public class Chest {
    final StructureLocation position;
    final ChestInfo info;
    private final Item[] items;
    public Chest(StructureLocation position, ChestInfo info, RandomGenerator<Item> generator) {
        this.info=info;
        this.position=position;
        items = new Item[info.getSize()];
        for (int i = 0; i < info.getSize(); i++) {
            items[i]=generator.getRandom(info.getQuality());
        }
    }
    public void feelOrDeleteBlock(GameWorldAccessor accessor) {
        Block block = accessor.getBlockAt(position, info.getPosition());
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
