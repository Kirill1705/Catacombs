package thor.core.generator.complete;

import lombok.Getter;
import thor.core.structure.PartTunnel;
import thor.core.structure.PlayerSpawnNode;
import thor.core.structure.Room;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class GameMap {
    @Getter
    private final MapField field;
    @Getter
    private final MapGraph graph;
    @Getter
    private final UUID uuid;

    public GameMap(Point size) {
        graph = new MapGraph();
        field = new MapField(size);
        uuid = UUID.randomUUID();
    }

    public boolean addRoom(Room room) {
        if (graph.canPlace(room, new Point(0, 0, 0).toBoundingBox(field.getSize().subtract(new Point(1, 1, 1))))) {
            field.feelMap(room.toBox());
            graph.addRoom(room);
            return true;
        }
        return false;
    }

    public void addEdge(Room source, Room dest, Collection<PartTunnel> tunnel) {
        graph.addEdge(source, dest, tunnel);
        for (PartTunnel partTunnel: tunnel) {
            field.feelMap(partTunnel.toBox());
        }
    }

    public List<PlayerSpawnNode> getPlayerSpawnPlaces() {
        List<PlayerSpawnNode> roomNodes = graph.getRooms().stream()
                .flatMap(room -> room.getPlayerSpawnPlaces().stream())
                .toList();
        List<PlayerSpawnNode> tunnelNodes = graph.getAllTunnels().stream()
                .flatMap(partTunnel -> partTunnel.getPlayerSpawnPlaces().stream())
                .toList();
        List<PlayerSpawnNode> result = new ArrayList<>();
        result.addAll(roomNodes);
        result.addAll(tunnelNodes);
        return result;
    }
}
