package thor.core.generator.tunnel.convert;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

public interface Converter {
    static Converter simple(BlockPosition position) {
        return new ConverterImpl(position, position.add(new Point(1, 1, 1)));
    }

    BlockPosition getBegin();
    BlockPosition convertVector(BlockPosition old);
    BlockPosition toNew(BlockPosition old);
    BlockPosition toOld(BlockPosition position);
}
