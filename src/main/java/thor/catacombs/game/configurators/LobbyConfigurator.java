package thor.catacombs.game.configurators;

import org.bukkit.structure.Structure;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public interface LobbyConfigurator {
    BlockPosition getLobbySpawnOffset();
    Structure getLobby();
}
