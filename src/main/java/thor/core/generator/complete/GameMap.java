package thor.core.generator.complete;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.structure.Structure;

import java.util.List;

public interface GameMap {
    void addStructure(Structure structure);

    boolean canAddByField(Structure structure);

    boolean canAddByOverlaps(Structure structure);

    List<Structure> getAllStructures();

    Point getSize();
}
