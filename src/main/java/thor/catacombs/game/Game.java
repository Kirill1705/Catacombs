package thor.catacombs.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import thor.catacombs.generator.map.GameMap;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import org.jetbrains.annotations.NotNull;
import thor.catacombs.game.configurators.Configurator;
import thor.catacombs.generator.*;
import thor.catacombs.generator.map.MapGenerator;
import thor.catacombs.generator.structures.PlayerSpawnable;
import thor.catacombs.generator.structures.Structure;
import thor.emptyMiniGame.MiniGame;
import thor.emptyMiniGame.MiniGameLoader;
import thor.usefulUtils.utils.OtherUtils;
import thor.usefulUtils.utils.StructureUtils;
import thor.usefulUtils.utils.dataStructures.ImmutableBox;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.*;
import java.util.function.Function;

public class Game implements MiniGame {
    private final Configurator configurator;
    private final BlockLocation location;
    private BlockLocation lobbyLoc;
    private final Map<Player, PlayerData> players = new HashMap<>();
    private GameStep step = GameStep.WAITING_FOR_PLAYERS;
    private final Timer timer;
    private final MiniGameLoader loader;
    private final Plugin plugin;
    private final GameMap map;
    private Scoreboard scoreboard;
    private Objective objective;
    private String prevTimeStr = "";
    public Map<Player, PlayerData> getPlayers() {
        return players;
    }
    private void placeLobby() {
        lobbyLoc = location.add(new Point(-50, 0, -50));
        StructureUtils.place(configurator.getLobby(), lobbyLoc.toLocation());
    }
    private void createScoreBoard() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("info", Criteria.PLAYER_KILL_COUNT, Component.text("Catacombs v2 | Kills").color(NamedTextColor.GOLD));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }
    public Game(Location location, MapGenerator generator, Configurator configurator, Plugin plugin, MiniGameLoader loader, FileConfiguration config) {
        this.location=new BlockLocation(location);
        this.configurator = configurator;
        this.plugin = plugin;
        this.loader = loader;
        this.timer = new Timer(plugin);
        createScoreBoard();
        placeLobby();
        map = generator.createMap(new BlockLocation(location));
    }
    public void updateTimeScore(String s) {
        if (!prevTimeStr.isEmpty()) {
            scoreboard.resetScores(prevTimeStr);
        }
        objective.getScore(s).setScore(configurator.getTotal()+1);
        prevTimeStr = s;
    }
    private void replaceTimer(int time, Function<Integer, Boolean> function) {
        timer.replaceTimer(time, 20, function);
    }
    private static String convertTime(int seconds) {
        return seconds/60+":"+seconds%60;
    }
    private void updateTimeForAllPlayers(int time, String name, Function<Integer, Boolean> function) {
        replaceTimer(time, (counter) -> {
            updateTimeScore(name+convertTime(counter));
            return function.apply(counter);
        });
    }
    private boolean shouldPlaceBedrock() {
        return new BlockLocation(0, 0, 0, loader.getWorld()).add(location).toLocation().getBlock().getType()!=Material.BEDROCK;
    }
    private void tptoArena() {

    }
    private void startGame() {
        step = GameStep.BATTLE;
        map.spawnPlayers(players.keySet());
        for (PlayerData data: players.values()) {
            data.startGame();
        }
        updateTimeForAllPlayers(configurator.getGameTime(), "Осталось: ", counter -> {
            if (counter==0) {
                return true;
            }
            return false;
        });
    }
    private void startBackCount() {
        map.place();
        updateTimeForAllPlayers(configurator.getWaitingTime(), "Начало через: ", (counter) -> {
            if (counter==0) {
                startGame();
                return true;
            }
            return false;
        });
    }
    @Override
    public void addPlayer(@NotNull Player player) {
        player.teleport(lobbyLoc.add(configurator.getLobbySpawnOffset()).toLocation());
        OtherUtils.resetPlayer(player);
        players.put(player, new PlayerData(player, this));
        player.setScoreboard(scoreboard);
        if (playersCount()>=2) {
            startBackCount();
        }
    }

    @Override
    public boolean waitingForPlayers() {
        return step==GameStep.WAITING_FOR_PLAYERS||step==GameStep.BACK_COUNT;
    }

    @Override
    public int playersCount() {
        return players.size();
    }

    @Override
    public boolean finished() {
        return step==GameStep.REMOVE;
    }
    public void finishGame(Player winner) {
        step=GameStep.FINISHING;
        winner.sendTitlePart(TitlePart.TITLE, Component.text("Вы победили!"));
        updateTimeForAllPlayers(10, "Результат: ", counter -> {
            if (counter==0) {
                step = GameStep.REMOVE;
                players.get(winner).removePlayer();
                players.clear();
                loader.removePlayer(winner);
                return true;
            }
            return false;
        });
    }
    @Override
    public void removePlayer(@NotNull Player player) {
        players.get(player).removePlayer();
        players.remove(player);
        if (players.size()==1) {
            finishGame(players.keySet().iterator().next());
        }
    }
}
