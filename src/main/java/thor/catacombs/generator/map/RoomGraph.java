package thor.catacombs.generator.map;

import org.bukkit.util.BoundingBox;
import thor.catacombs.generator.Tunnel;
import thor.catacombs.generator.structures.Room;
import thor.catacombs.generator.structures.Structure;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

import java.util.*;

public class RoomGraph implements ImmutableGraph {
    private final Map<Room, ArrayList<Edge>> rooms = new LinkedHashMap<>();
    private final boolean[][][] map;
    private final BlockPosition size;
    private final BoundingBox box;
    public BlockPosition getMapSize() {
        return size;
    }
    public RoomGraph(BlockPosition size) {
        map = new boolean[size.x()][size.y()][size.z()];
        this.size=size;
        box = new BoundingBox(1, 1, 1, size.x(), size.y(), size.z());
    }
    public Iterable<Edge> getEdges(Room room) {
        if (rooms.containsKey(room)) {
            return Collections.unmodifiableCollection(rooms.get(room));
        }
        return Collections.emptyList();
    }
    public int getSize() {
        return rooms.size();
    }
    public List<Room> sortByDistance(Room structure, List<Room> structures) {
        var result = new ArrayList<>(structures);
        result.sort(Comparator.comparingLong(value -> (long) value.getPosition().distanceSquared(structure.getPosition())));
        result.removeFirst();
        return result;
    }
    private void feelMap(Iterable<BlockPosition> box) {
        for (BlockPosition vector: box) {
            if (this.box.contains(vector.toBlockVector())) {
                map[vector.x()][vector.y()][vector.z()] = true;
            }
        }
    }
    public boolean canPlaceByMap(Iterable<BlockPosition> structure) {
        for (BlockPosition vector: structure) {
            if (!box.contains(vector.toBlockVector())||map[vector.x()][vector.y()][vector.z()]) {
                return false;
            }
        }
        return true;
    }
    public boolean canPlace(Structure structure) {
        BoundingBox box = structure.getOffsetBox().toBoundingBox();
        if (!this.box.contains(box)) return false;
        for (Structure room: rooms.keySet()) {
            if (box.overlaps(room.getOffsetBox().toBoundingBox())) {
                return false;
            }
        }
        return true;
    }
    public void addRoom(Room room) {
        feelMap(room.getOffsetBox());
        rooms.put(room, new ArrayList<>());
    }
    public Set<Room> getRooms() {
        return Collections.unmodifiableSet(rooms.keySet());
    }

    public void addEdge(Room from, Room to, Tunnel edge) {
        for (Structure part: edge.data()) {
            feelMap(part.getOffsetBox());
        }
        rooms.get(from).add(new Edge(from, to, edge));
        rooms.get(to).add(new Edge(to, from, edge));
    }
    public List<Structure> getAllStructures() {
        List<Structure> result = new ArrayList<>();
        Set<Tunnel> visited = new HashSet<>();
        for (Map.Entry<Room, ArrayList<Edge>> entry: rooms.entrySet()) {
            result.add(entry.getKey());
            for (Edge edge: entry.getValue()) {
                if (!visited.contains(edge.edge)) {
                    visited.add(edge.edge);
                    result.addAll(edge.edge.data());
                }
            }
        }
        return result;
    }

    public record Edge(Room from, Room to, Tunnel edge) { }
}
