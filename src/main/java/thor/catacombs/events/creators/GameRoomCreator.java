package thor.catacombs.events.creators;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.generator.ItemGeneratorHolder;
import thor.catacombs.generator.structures.GameRoom;
import thor.catacombs.generator.structures.GameTunnelPart;
import thor.catacombs.generator.structures.Room;
import thor.catacombs.generator.structures.TunnelPart;
import thor.catacombs.info.structure.interfaces.PartTunnelInfo;
import thor.catacombs.info.structure.interfaces.RoomInfo;

public record GameRoomCreator(ItemGeneratorHolder generatorHolder) implements RoomCreator, PartTunnelCreator {

    @Override
    public TunnelPart create(PartTunnelInfo info, BlockPosition position) {
        return new GameTunnelPart(info, position, generatorHolder);
    }

    @Override
    public Room create(RoomInfo info, BlockPosition position) {
        return new GameRoom(info, position, generatorHolder);
    }
}
