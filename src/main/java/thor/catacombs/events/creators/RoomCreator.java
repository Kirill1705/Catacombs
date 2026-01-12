package thor.catacombs.events.creators;

import thor.catacombs.generator.structures.Room;
import thor.catacombs.info.structure.interfaces.RoomInfo;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public interface RoomCreator {
    Room create(RoomInfo info, BlockPosition position);
}
