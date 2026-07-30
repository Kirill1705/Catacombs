package thor.core.generator.tunnel.convert;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.List;

public class ConverterChain implements Converter{
    private final List<Converter> converters;

    public ConverterChain(List<Converter> converters) {
        this.converters = converters;
    }

    @Override
    public Point getBegin() {
        return toOld(new Point(0, 0, 0));
    }

    @Override
    public Point convertVector(Point old) {
        for (Converter converter: converters) {
            old = converter.convertVector(old);
        }
        return old;
    }

    @Override
    public Point toNew(Point old) {
        for (Converter converter: converters) {
            old = converter.toNew(old);
        }
        return old;
    }

    @Override
    public Point toOld(Point position) {
        for (Converter converter: converters) {
            position = converter.toOld(position);
        }
        return position;
    }
}
