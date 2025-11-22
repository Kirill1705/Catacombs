package thor.catacombs.generator.structures;

import org.bukkit.Location;
import thor.usefulUtils.utils.dataStructures.*;
import thor.catacombs.generator.Chest;
import thor.catacombs.info.structure.interfaces.StructureInfo;

import java.util.Collection;

public interface Structure extends StructureInfo {
    BlockPosition getPosition();
    BlockLocation getLocation(BlockLocation begin);
    void place(BlockLocation location);
    default OffsetBox getOffsetBox() {
        BlockPosition position = getPosition();
        return new ImmutableOffsetBox(position, position.add(getSize()).subtract(new Point(1, 1, 1)));
    }
    Collection<Chest> getChests();
    StructureInfo getInfo();
}
