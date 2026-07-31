package thor.core.generator.complete;

import lombok.Getter;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.structure.PartTunnel;
import thor.core.structure.Room;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.structure.create.StructurePartsHolder;

import java.util.Collection;
import java.util.UUID;

public class GameMap {
    @Getter
    private final MapField field;
    @Getter
    private final MapGraph graph;
    @Getter
    private final UUID uuid;
    @Getter
    private final StructurePartsHolder partsHolder;

    public GameMap(Point size, StructurePartsHolder partsHolder) {
        this.partsHolder = partsHolder;
        graph = new MapGraph();
        field = new MapField(size);
        uuid = UUID.randomUUID();
    }

    public boolean addRoom(Room room) {
        if (graph.canPlace(room, new Point(0, 0, 0).toBoundingBox(field.getSize().subtract(new Point(1, 1, 1))))) {
            field.feelMap(room.toBox());
            graph.addRoom(room);
            partsHolder.createFromRoomInfo(Converter.simple(room.getPosition()), room.getRoomInfo());
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
}
