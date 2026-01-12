package thor.catacombs.events.creators;

import thor.catacombs.generator.ItemGeneratorHolder;
import thor.catacombs.generator.structures.GameRoom;
import thor.catacombs.generator.structures.GameTunnelPart;
import thor.catacombs.generator.structures.Room;
import thor.catacombs.generator.structures.TunnelPart;
import thor.catacombs.generator.structures.utils.StructureLocation;
import thor.catacombs.info.structure.interfaces.PartTunnelInfo;
import thor.catacombs.info.structure.interfaces.RoomInfo;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.ImmutableOffsetBox;

public record GameRoomCreator(ItemGeneratorHolder generatorHolder) implements RoomCreator, PartTunnelCreator {

    @Override
    public TunnelPart create(PartTunnelInfo info, BlockPosition position) {
        return new GameTunnelPart(info, new StructureLocation(ImmutableOffsetBox.fromBeginAndSize(position, info.getSize())), generatorHolder);
    }

    @Override
    public Room create(RoomInfo info, BlockPosition position) {
        return new GameRoom(info, new StructureLocation(ImmutableOffsetBox.fromBeginAndSize(position, info.getSize())), generatorHolder);
    }
}
