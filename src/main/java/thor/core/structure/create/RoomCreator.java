package thor.core.structure.create;

import thor.core.info.RoomInfo;
import thor.core.structure.Room;
import thor.usefulUtils.utils.dataStructures.Point;

public interface RoomCreator {
    Room create(Point position, RoomInfo info);
}
