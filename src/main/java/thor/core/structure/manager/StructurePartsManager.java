package thor.core.structure.manager;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.structure.Island;
import thor.core.structure.Room;

public interface StructurePartsManager {
    void addFromRoom(Room room, Point mapPosition, String worldName);

    void addFromIslandInfo(Island island, Point mapPosition, String worldName);
}
