package thor.core.structure;

import thor.core.port.output.WorldAccessor;
import thor.core.structure.chest.Chest;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

import java.util.Collection;

public interface Structure extends Iterable<BlockPosition> {
    BlockPosition getPosition();
    BlockPosition getSize();
    Collection<Chest> getChests();
    Collection<PlayerSpawnNode> getPlayerSpawnPlaces();
    void place(WorldAccessor accessor);
}
