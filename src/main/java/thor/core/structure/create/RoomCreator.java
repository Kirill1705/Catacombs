package thor.core.structure.create;

import thor.core.info.RoomInfo;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.structure.Room;

public interface RoomCreator {
    Room create(Point position, RoomInfo info);
}
