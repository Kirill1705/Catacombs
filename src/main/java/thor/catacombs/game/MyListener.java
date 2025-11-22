package thor.catacombs.game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import thor.emptyMiniGame.LobbyInterface;
import thor.emptyMiniGame.MiniGameLoader;
import thor.lobby.PlayerLobbyEvent;

public class MyListener implements Listener {
    private final MiniGameLoader loader;
    public MyListener(MiniGameLoader loader) {
        this.loader=loader;
    }
    @EventHandler
    public void onLobby(PlayerLobbyEvent event) {
        loader.playerUsedLobbyCommand(new LobbyInterface() {
            @Override
            public void setItem(ItemStack itemStack) {
                event.getItemsInLobby().add(itemStack);
            }

            @Override
            public Player getPlayer() {
                return event.getPlayer();
            }
        });
    }
}
