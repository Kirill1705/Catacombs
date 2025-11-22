package thor.catacombs.generator.items;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import thor.customFeatures.items.ExtendedItemStack;

public class EnchantedBook implements Item {
    private final Item item;
    public EnchantedBook(String name, int weight, int level) {
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemStack.getItemMeta();
        Enchantment enchantment = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).get(NamespacedKey.minecraft(name));
        if (enchantment==null) throw new RuntimeException("invalid enchantment name "+name);
        meta.addStoredEnchant(enchantment, level, true);
        item = new GameItem(1, 1, new ExtendedItemStack(itemStack), weight);
    }

    @Override
    public ExtendedItemStack getItemStack() {
        return item.getItemStack();
    }

    @Override
    public int getQuantity() {
        return item.getQuantity();
    }

    @Override
    public int getWeight() {
        return item.getWeight();
    }
}
