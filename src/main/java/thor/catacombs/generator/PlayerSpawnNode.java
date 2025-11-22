package thor.catacombs.generator;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.info.block.PlayerSpawnInfo;

public class PlayerSpawnNode {
    private final PlayerSpawnInfo info;
    private final BlockPosition position;

    public boolean isClosed() {
        return closed;
    }

    private boolean closed = false;
    public PlayerSpawnNode(PlayerSpawnInfo info, BlockPosition position) {
        this.info=info;
        this.position = position.add(info.getPosition());
    }
    public void spawnPlayer(BlockLocation location, Player player) {
        closed = true;
        player.teleport(location.add(position).toLocation());
    }
}
