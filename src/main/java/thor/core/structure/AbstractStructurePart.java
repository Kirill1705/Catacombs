package thor.core.structure;

import lombok.Getter;
import thor.core.generator.tunnel.convert.Converter;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public abstract class AbstractStructurePart implements StructurePart {
    @Getter
    private final Point position;
    @Getter
    private final String worldName;

    public AbstractStructurePart(StructurePartPlaceInfo info, Point localPosition) {
        this.position = info.converter().toOld(localPosition).add(info.mapPosition());
        this.worldName = info.worldName();
    }
}
