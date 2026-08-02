package thor.core.structure;

import thor.core.generator.tunnel.convert.Converter;
import thor.core.port.output.StructureManager;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.chest.Chest;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.Collection;

public interface Structure {
    Converter getPosition();
    Point getSize();
    ImmutableBox toBox();
    void place(WorldAccessor accessor, StructureManager structureManager);

    void accept(StructureVisitor visitor);
}
