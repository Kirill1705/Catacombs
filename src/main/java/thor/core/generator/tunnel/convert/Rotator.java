package thor.core.generator.tunnel.convert;

import thor.usefulUtils.utils.dataStructures.Point;

public class Rotator implements Converter {
    private final Converter converter;

    public Rotator(Converter converter) {
        this.converter = converter;
    }

    @Override
    public Point getBegin() {
        return converter.getBegin();
    }

    @Override
    public Point convertVector(Point old) {
        return converter.convertVector(rotate(old));
    }

    @Override
    public Point toNew(Point old) {
        return converter.toNew(rotate(old));
    }

    @Override
    public Point toOld(Point position) {
        return converter.toOld(rotate(position));
    }

    private Point rotate(Point position) {
        return new Point(position.z(), position.y(), position.x());
    }
}
