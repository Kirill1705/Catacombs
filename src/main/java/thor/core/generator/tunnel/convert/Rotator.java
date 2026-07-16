package thor.core.generator.tunnel.convert;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

public class Rotator implements Converter {
    private final Converter converter;

    public Rotator(Converter converter) {
        this.converter = converter;
    }

    @Override
    public BlockPosition getBegin() {
        return converter.getBegin();
    }

    @Override
    public BlockPosition convertVector(BlockPosition old) {
        return converter.convertVector(rotate(old));
    }

    @Override
    public BlockPosition toNew(BlockPosition old) {
        return converter.toNew(rotate(old));
    }

    @Override
    public BlockPosition toOld(BlockPosition position) {
        return converter.toOld(rotate(position));
    }

    private BlockPosition rotate(BlockPosition position) {
        return new Point(position.z(), position.y(), position.x());
    }
}
