package thor.core.structure;

import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableLocation;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.port.output.StructureManager;
import thor.core.port.output.WorldAccessor;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public interface Structure {
    Point getPosition();
    Converter getConverter();
    Point getSize();
    ImmutableBox toBox();
    void place(WorldAccessor accessor, StructureManager structureManager, ImmutableLocation location);

    void accept(StructureVisitor visitor);
}
