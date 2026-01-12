package thor.catacombs.generator.structures;

import thor.catacombs.generator.Chest;
import thor.catacombs.generator.structures.utils.GameWorldAccessor;
import thor.catacombs.info.structure.interfaces.StructureInfo;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.ImmutableOffsetBox;
import thor.usefulUtils.utils.dataStructures.OffsetBox;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.Collection;

public interface Structure extends StructureInfo {
    BlockPosition getPosition();
    void place(GameWorldAccessor accessor);
    default OffsetBox getOffsetBox() {
        BlockPosition position = getPosition();
        return new ImmutableOffsetBox(position, position.add(getSize()).subtract(new Point(1, 1, 1)));
    }
    Collection<Chest> getChests();
    StructureInfo getInfo();
}
