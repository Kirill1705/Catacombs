package thor.core.structure;

import lombok.Getter;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.StructureInfo;
import thor.core.port.output.StructureManager;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.chest.Chest;
import thor.core.structure.chest.ItemCreator;
import ru.vikhrenko.serverUtils.utils.dataStructures.Boxes;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.Collection;
import java.util.List;

public abstract class AbstractStructure implements Structure {
    private final Point position;
    private final Point size;
    private final List<Chest> chests;
    private final List<PlayerSpawnNode> playerSpawnNodes;
    private final boolean rotated;
    @Getter
    private final String textId;

    public AbstractStructure(ItemCreator generator, Converter converter, StructureInfo structureInfo, boolean rotated) {
        this.rotated = rotated;
        Point end = converter.toOld(structureInfo.getSize().subtract(new Point(1, 1, 1)));
        this.position = end.min(converter.getBegin());
        this.size = end.size(converter.getBegin());
        this.textId = structureInfo.getTextId();
        chests = structureInfo.getChests().stream()
                .map(chestInfo -> new Chest(converter, chestInfo, generator))
                .toList();
        playerSpawnNodes = structureInfo.getPlayerSpawnPlaces().stream()
                .map(playerSpawnPlaceInfo -> new PlayerSpawnNode(converter, playerSpawnPlaceInfo))
                .toList();
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public Point getSize() {
        return size;
    }

    @Override
    public Collection<Chest> getChests() {
        return chests;
    }

    @Override
    public Collection<PlayerSpawnNode> getPlayerSpawnPlaces() {
        return playerSpawnNodes;
    }

    @Override
    public void place(WorldAccessor accessor, StructureManager structureManager) {
        place(accessor, structureManager, rotated);
        chests.forEach(chest -> chest.place(accessor));
    }

    @Override
    public ImmutableBox toBox() {
        return Boxes.fromBeginAndSize(position, size);
    }

    protected abstract void place(WorldAccessor accessor, StructureManager structureManager, boolean rotated);
}
