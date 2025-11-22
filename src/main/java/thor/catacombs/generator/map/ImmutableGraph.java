package thor.catacombs.generator.map;

import thor.catacombs.generator.structures.Room;
import thor.catacombs.generator.structures.Structure;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

import java.util.Collection;

public interface ImmutableGraph {
    boolean canPlaceByMap(Iterable<BlockPosition> structure);
    int getSize();
    BlockPosition getMapSize();
    Iterable<RoomGraph.Edge> getEdges(Room room);
    boolean canPlace(Structure structure);
    Collection<Room> getRooms();
}
