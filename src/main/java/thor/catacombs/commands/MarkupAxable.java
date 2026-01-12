package thor.catacombs.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class MarkupAxable implements Listener {
    public MarkupAxable(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    protected final Map<UUID, Session> sessions = new HashMap<>();
    protected void giveMarkupAxe(Player player) {
        ItemStack axe = new ItemStack(Material.WOODEN_AXE);
        ItemMeta meta = axe.getItemMeta();
        meta.displayName(Component.text("Markup Tool").color(NamedTextColor.GOLD));
        meta.lore(Collections.singletonList(Component.text("Используйте для размещения разметки").color(NamedTextColor.GRAY)));
        axe.setItemMeta(meta);
        player.getInventory().addItem(axe);
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.WOODEN_AXE) return;
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) return;
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        Block block = event.getClickedBlock();
        if (block == null) return;
        if (sessions.containsKey(playerId)) {
            event.setCancelled(true);
            Location location = block.getLocation();
            Session session = sessions.get(playerId);
            if (session.removeByClicked(location, player)) {
                item.setAmount(item.getAmount() - 1);
                sessions.remove(playerId);
            }
        }
    }
    protected interface Session {
        boolean removeByClicked(Location location, Player player);
    }
}
