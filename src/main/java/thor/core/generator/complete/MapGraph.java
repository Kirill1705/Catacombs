package thor.core.generator.complete;

import org.bukkit.util.BoundingBox;
import thor.core.structure.PartTunnel;
import thor.core.structure.Room;
import thor.core.structure.Structure;
import thor.usefulUtils.utils.dataStructures.ImmutableOffsetBox;

import java.util.*;
import java.util.stream.Collectors;

public class MapGraph {
    private final Map<Room, List<Edge>> rooms = new LinkedHashMap<>();

    public void addEdge(Room from, Room to, Collection<PartTunnel> tunnel) {
        rooms.get(from).add(new Edge(from, to, tunnel));
        rooms.get(to).add(new Edge(to, from, tunnel));
    }

    public Set<Edge> getEdges() {
        return rooms.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    public void addRoom(Room room) {
        rooms.put(room, new ArrayList<>());
    }

    public boolean canPlace(Structure structure, BoundingBox mapBox) {
        BoundingBox box = ImmutableOffsetBox.fromBeginAndSize(structure.getPosition(), structure.getSize()).toBoundingBox();
        if (!mapBox.contains(box)) return false;
        for (Structure room: rooms.keySet()) {
            if (box.overlaps(ImmutableOffsetBox.fromBeginAndSize(room.getPosition(), room.getSize()).toBoundingBox())) {
                return false;
            }
        }
        return true;
    }

    public Collection<PartTunnel> getEdge(Room from, Room to) {
        if (!rooms.containsKey(from)) {
            return null;
        }
        Optional<Edge> edgeOptional = rooms.get(from).stream()
                .filter(edge -> edge.to()==to)
                .findAny();
        return edgeOptional.map(Edge::tunnels).orElse(null);
    }

    public Collection<Room> getRooms() {
        return Collections.unmodifiableCollection(rooms.keySet());
    }

    public Collection<PartTunnel> getAllTunnels() {
        List<PartTunnel> result = new ArrayList<>();
        Set<Edge> visited = new HashSet<>();
        for (var edges: rooms.values()) {
            for (Edge edge: edges) {
                if (!visited.contains(edge)) {
                    result.addAll(edge.tunnels());
                }
                visited.add(edge);
            }
        }
        return result;
    }
}
