package thor.core.structure.create;

import thor.core.info.RoomInfo;
import thor.core.structure.Room;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public interface RoomCreator {
    Room create(BlockPosition position, RoomInfo info);
}
