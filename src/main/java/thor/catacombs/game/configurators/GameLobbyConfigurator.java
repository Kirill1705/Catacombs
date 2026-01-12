package thor.catacombs.game.configurators;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.structure.Structure;
import thor.emptyMiniGame.MiniGameEnvironment;
import thor.usefulUtils.utils.OtherUtils;
import thor.usefulUtils.utils.StructureUtils;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.ImmutableBox;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.List;

public class GameLobbyConfigurator implements LobbyConfigurator {
    @Override
    public Structure getLobby() {
        return lobby;
    }

    @Override
    public BlockPosition getLobbySpawnOffset() {
        return lobbySpawnOffset;
    }

    private final Structure lobby;
    private Point lobbySpawnOffset = new Point(3, 3, 3);

    public GameLobbyConfigurator(MiniGameEnvironment loader, FileConfiguration config, Plugin plugin) {
        BlockLocation zero = new BlockLocation(0, 0, 0, loader.getWorld());
        if (zero.toLocation().getBlock().getType() == Material.AIR) {
            lobby = StructureUtils.load(new NamespacedKey(plugin, "lobby"));
            if (lobby == null) {
                throw new RuntimeException("No lobby!");
                //OtherUtils.fill(Pair.of(zero, zero.clone().add(10, 10, 10)), Material.BEDROCK);
                //OtherUtils.fill(Pair.of(zero.clone().add(1, 1, 1), zero.clone().add(9, 9, 9)), Material.AIR);
            }
        } else {
            ImmutableBox box = OtherUtils.getBox(zero, block -> block.getType() != Material.AIR);
            lobby = StructureUtils.save(box, new NamespacedKey(plugin, "lobby"));
        }
        OtherUtils.configureVariable("lobby-offset", name -> {
            List<Integer> list = config.getIntegerList(name);
            lobbySpawnOffset = new Point(list.get(0), list.get(1), list.get(2));
        }, config);
    }
}
