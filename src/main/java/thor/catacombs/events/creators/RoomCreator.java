package thor.catacombs.events.creators;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.generator.structures.Room;
import thor.catacombs.info.structure.interfaces.RoomInfo;

public interface RoomCreator {
    Room create(RoomInfo info, BlockPosition position);
}
