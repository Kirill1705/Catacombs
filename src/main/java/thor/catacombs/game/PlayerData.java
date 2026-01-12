package thor.catacombs.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import thor.usefulUtils.utils.OtherUtils;

public class PlayerData {
    public Player getPlayer() {
        return player;
    }
    private final Player player;
    private final Game game;
    public PlayerData(Player player, Game game) {
        this.player = player;
        this.game = game;
    }
    public void startGame() {
        OtherUtils.resetPlayer(player);
        player.sendTitlePart(TitlePart.TITLE, Component.text("Игра началась!"));
        player.sendActionBar(Component.text("Лутайте сундуки и выживите последним!"));
    }
    public void removePlayer(boolean emergency) {
        if (emergency) {
            player.sendMessage(Component.text("The game has finished because of error, please try to start game again"));
        }
        OtherUtils.resetPlayer(player);
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
}
