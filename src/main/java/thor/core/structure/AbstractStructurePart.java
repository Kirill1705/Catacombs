package thor.core.structure;

import lombok.Getter;
import thor.core.generator.tunnel.convert.Converter;
import thor.usefulUtils.utils.dataStructures.Point;

public abstract class AbstractStructurePart {
    @Getter
    private final Point position;

    public AbstractStructurePart(Converter converter, Point localPosition) {
        this.position = converter.toOld(localPosition);
    }
}
