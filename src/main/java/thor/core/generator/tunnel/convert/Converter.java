package thor.core.generator.tunnel.convert;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public interface Converter {
    static Converter simple(Point position) {
        return new ConverterImpl(position, position.add(new Point(1, 1, 1)));
    }

    Point getBegin();
    Point convertVector(Point old);
    Point toNew(Point old);
    Point toOld(Point position);
}
