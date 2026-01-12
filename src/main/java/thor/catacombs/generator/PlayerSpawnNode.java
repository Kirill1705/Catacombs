package thor.catacombs.generator;

import org.bukkit.entity.Player;
import thor.catacombs.generator.structures.utils.GameWorldAccessor;
import thor.catacombs.generator.structures.utils.StructureLocation;
import thor.catacombs.info.block.PlayerSpawnInfo;

public class PlayerSpawnNode {
    private final PlayerSpawnInfo info;
    private final StructureLocation position;

    public boolean isClosed() {
        return closed;
    }

    private boolean closed = false;
    public PlayerSpawnNode(PlayerSpawnInfo info, StructureLocation position) {
        this.info=info;
        this.position = position;
    }
    public void spawnPlayer(GameWorldAccessor accessor, Player player) {
        closed = true;
        player.teleport(accessor.convert(position, info.getPosition()).toLocation());
    }
}
