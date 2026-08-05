package thor.core.structure.create;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.structure.Structure;

public interface StructureCreator {
    Structure create(Point position);
}
