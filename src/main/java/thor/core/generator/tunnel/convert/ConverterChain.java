package thor.core.generator.tunnel.convert;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.List;

public class ConverterChain implements Converter{
    private final List<Converter> converters;

    public ConverterChain(List<Converter> converters) {
        this.converters = converters;
    }

    @Override
    public BlockPosition getBegin() {
        return toOld(new Point(0, 0, 0));
    }

    @Override
    public BlockPosition convertVector(BlockPosition old) {
        for (Converter converter: converters) {
            old = converter.convertVector(old);
        }
        return old;
    }

    @Override
    public BlockPosition toNew(BlockPosition old) {
        for (Converter converter: converters) {
            old = converter.toNew(old);
        }
        return old;
    }

    @Override
    public BlockPosition toOld(BlockPosition position) {
        for (Converter converter: converters) {
            position = converter.toOld(position);
        }
        return position;
    }
}
