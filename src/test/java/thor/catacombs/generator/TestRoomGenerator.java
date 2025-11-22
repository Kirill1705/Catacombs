package thor.catacombs.generator;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.events.creators.RoomCreator;
import thor.catacombs.generator.map.GeneratorConfigurator;
import thor.catacombs.generator.map.RoomGenerator;
import thor.catacombs.generator.map.RoomGraph;
import thor.catacombs.generator.structures.Room;
import thor.catacombs.info.structure.interfaces.RoomInfo;

import java.util.List;

public record TestRoomGenerator(Iterable<? extends BlockPosition> coords, GeneratorConfigurator configurator, List<RoomInfo> info, RoomCreator creator) implements RoomGenerator {
    @Override
    public RoomGraph generate() {
        RoomGraph rooms = new RoomGraph(configurator.getSize());
        var generator = new ChanceGenerator<>(info);
        for (BlockPosition position: coords) {
            RoomInfo roomInfo = generator.getRandom();
            Room room = creator.create(roomInfo, position);
            rooms.addRoom(room);
        }
        return rooms;
    }
}
