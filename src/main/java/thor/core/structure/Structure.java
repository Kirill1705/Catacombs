package thor.core.structure;

import thor.core.port.output.WorldAccessor;
import thor.core.structure.chest.Chest;
import thor.usefulUtils.utils.dataStructures.ImmutableBox;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.Collection;

public interface Structure {
    Point getPosition();
    Point getSize();
    ImmutableBox toBox();
    Collection<Chest> getChests();
    Collection<PlayerSpawnNode> getPlayerSpawnPlaces();
    void place(WorldAccessor accessor);
}
