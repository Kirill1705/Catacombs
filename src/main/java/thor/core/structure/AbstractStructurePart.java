package thor.core.structure;

import lombok.Getter;
import thor.core.generator.tunnel.convert.Converter;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public abstract class AbstractStructurePart {
    @Getter
    private final BlockPosition position;

    public AbstractStructurePart(Converter converter, BlockPosition localPosition) {
        this.position = converter.toOld(localPosition);
    }
}
