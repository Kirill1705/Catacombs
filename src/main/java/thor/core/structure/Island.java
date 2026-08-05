package thor.core.structure;

import lombok.Getter;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableLocation;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.IslandInfo;
import thor.core.port.output.StructureManager;
import thor.core.port.output.WorldAccessor;

public class Island extends AbstractStructure {
    @Getter
    private final IslandInfo islandInfo;

    public Island(Converter converter, IslandInfo islandInfo) {
        super(converter, islandInfo, false);
        this.islandInfo = islandInfo;
    }

    @Override
    public void place(WorldAccessor accessor, StructureManager structureManager, ImmutableLocation location) {
        String path = structureManager.getIslandStructurePath(getTextId());
        accessor.placeStructure(path, getPosition().add(location.position()), isRotated(), location.worldName());
    }

    @Override
    public void accept(StructureVisitor visitor) {
        visitor.visit(this);
    }
}
