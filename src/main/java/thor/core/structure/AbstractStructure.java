package thor.core.structure;

import lombok.Getter;
import org.jspecify.annotations.NonNull;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.StructureInfo;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.chest.Chest;
import thor.core.structure.chest.ItemCreator;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractStructure implements Structure {
    private final BlockPosition position;
    private final BlockPosition size;
    private final List<Chest> chests;
    private final List<PlayerSpawnNode> playerSpawnNodes;
    private final boolean rotated;
    @Getter
    private final String textId;

    public AbstractStructure(ItemCreator generator, Converter converter, StructureInfo structureInfo, boolean rotated) {
        this.rotated = rotated;
        BlockPosition end = converter.toOld(structureInfo.getSize().subtract(new Point(1, 1, 1)));
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
    public BlockPosition getPosition() {
        return position;
    }

    @Override
    public BlockPosition getSize() {
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
    public void place(WorldAccessor accessor) {
        place(accessor, rotated);
        chests.forEach(chest -> chest.place(accessor));
    }

    @Override
    public @NonNull Iterator<BlockPosition> iterator() {
        return new BlockPositionIterator(position, size);
    }

    protected abstract void place(WorldAccessor accessor, boolean rotated);

    private static class BlockPositionIterator implements Iterator<BlockPosition> {
        private final BlockPosition begin;
        private final BlockPosition end;
        private BlockPosition current;

        public BlockPositionIterator(BlockPosition begin, BlockPosition size) {
            end = begin.add(size).subtract(new Point(1, 1, 1));
            this.begin = begin;
            current = begin;
        }

        @Override
        public boolean hasNext() {
            return current.notMore(end);
        }

        @Override
        public BlockPosition next() {
            BlockPosition toReturn = current;
            if (current.x() < end.x()) {
                current = current.add(new Point(1, 0, 0));
            }
            else if (current.y() < end.y()) {
                current = new Point(begin.x(), current.y() + 1, current.z());
            }
            else {
                current = new Point(begin.x(), begin.y(), current.z() + 1);
            }
            return toReturn;
        }
    }
}
