package thor.catacombs.application;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import thor.emptyMiniGame.MiniGameConfig;
import thor.emptyMiniGame.MiniGameEnvironment;
import thor.usefulUtils.utils.ItemUtils;

public class MiniGameConfigFacade {
    private final MiniGameEnvironment environment;
    public MiniGameEnvironment getEnvironment() {
        return environment;
    }

    public MiniGameConfigFacade(Plugin plugin) {
        MiniGameConfig config = MiniGameConfig.builder().name("catacombs").startItem(startItem()).autoRemovePlayerByDeath(true).build();
        environment = new MiniGameEnvironment(config, plugin);
    }

    public static ItemStack startItem() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(Component.text("Играть в катакомбы").color(NamedTextColor.YELLOW));
        ItemUtils.setCustomModelData(meta, "1");
        itemStack.setItemMeta(meta);
        itemStack.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
        return itemStack;
    }
}
