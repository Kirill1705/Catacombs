package thor.core.generator.complete;

import lombok.Getter;
import thor.core.structure.PartTunnel;
import thor.core.structure.Room;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.Collection;

public class GameMap {
    @Getter
    private final MapField field;
    @Getter
    private final MapGraph graph;

    public GameMap(BlockPosition size) {
        graph = new MapGraph();
        field = new MapField(size);
    }

    public boolean addRoom(Room room) {
        if (graph.canPlace(room, new Point(0, 0, 0).toBoundingBox(field.getSize().subtract(new Point(1, 1, 1))))) {
            field.feelMap(room);
            graph.addRoom(room);
            return true;
        }
        return false;
    }

    public void addEdge(Room source, Room dest, Collection<PartTunnel> tunnel) {
        graph.addEdge(source, dest, tunnel);
        for (PartTunnel partTunnel: tunnel) {
            field.feelMap(partTunnel);
        }
    }
}
