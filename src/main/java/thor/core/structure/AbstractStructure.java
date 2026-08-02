package thor.core.structure;

import lombok.Getter;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.StructureInfo;
import thor.core.port.output.StructureManager;
import thor.core.port.output.WorldAccessor;
import ru.vikhrenko.serverUtils.utils.dataStructures.Boxes;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public abstract class AbstractStructure implements Structure {
    private final Point position;
    private final Point size;
    private final boolean rotated;
    @Getter
    private final String textId;
    private final Converter converter;

    public AbstractStructure(Converter converter, StructureInfo structureInfo, boolean rotated) {
        this.rotated = rotated;
        Point end = converter.toOld(structureInfo.getSize().subtract(new Point(1, 1, 1)));
        this.position = end.min(converter.getBegin());
        this.size = end.size(converter.getBegin());
        this.textId = structureInfo.getTextId();
        this.converter = converter;
    }

    @Override
    public Converter getPosition() {
        return converter;
    }

    @Override
    public Point getSize() {
        return size;
    }

    @Override
    public void place(WorldAccessor accessor, StructureManager structureManager) {
        place(accessor, structureManager, rotated);
    }

    @Override
    public ImmutableBox toBox() {
        return Boxes.fromBeginAndSize(position, size);
    }

    protected abstract void place(WorldAccessor accessor, StructureManager structureManager, boolean rotated);
}
