package thor.core.generator.complete;

import lombok.Getter;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.structure.Structure;

import java.util.List;
import java.util.UUID;

public class GameMapImpl implements GameMap {
    @Getter
    private final MapField field;
    @Getter
    private final MapGraph graph;
    @Getter
    private final UUID uuid;

    public GameMapImpl(Point size) {
        graph = new MapGraph();
        field = new MapField(size);
        uuid = UUID.randomUUID();
    }

    public void addStructure(Structure structure) {
        graph.addStructure(structure);
        field.feelMap(structure.toBox());
    }

    @Override
    public boolean canAddByField(Structure structure) {
        return field.canPlaceByMap(structure.toBox());
    }

    @Override
    public boolean canAddByOverlaps(Structure structure) {
        return graph.canPlace(structure, new Point(0, 0, 0).toBoundingBox(field.getSize().subtract(new Point(1, 1, 1))));
    }

    @Override
    public List<Structure> getAllStructures() {
        return graph.getRooms();
    }

    @Override
    public Point getSize() {
        return field.getSize();
    }
}
