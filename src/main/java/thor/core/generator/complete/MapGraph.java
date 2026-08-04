package thor.core.generator.complete;

import org.bukkit.util.BoundingBox;
import thor.core.structure.Structure;
import ru.vikhrenko.serverUtils.utils.dataStructures.Boxes;

import java.util.*;

public class MapGraph {
    private final List<Structure> rooms = new ArrayList<>();

    public void addStructure(Structure room) {
        rooms.add(room);
    }

    public boolean canPlace(Structure structure, BoundingBox mapBox) {
        BoundingBox box = structure.toBox().toBoundingBox();
        if (!mapBox.contains(box)) return false;
        for (Structure room: rooms) {
            if (box.overlaps(room.toBox().toBoundingBox())) {
                return false;
            }
        }
        return true;
    }

    public List<Structure> getRooms() {
        return Collections.unmodifiableList(rooms);
    }
}
