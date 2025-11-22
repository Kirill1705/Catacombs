package thor.catacombs.generator.map;

import thor.catacombs.events.creators.RoomCreator;
import thor.catacombs.generator.ChanceGenerator;
import thor.catacombs.generator.structures.Room;
import thor.catacombs.info.structure.interfaces.RoomInfo;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.List;

public record GameRoomGenerator(GeneratorConfigurator configurator, List<RoomInfo> info, RoomCreator creator) implements RoomGenerator {
    @Override
    public RoomGraph generate() {
        RoomGraph rooms = new RoomGraph(configurator.getSize());
        var generator = new ChanceGenerator<>(info);
        BlockPosition size = configurator.getSize();
        int roomsCount = (int) ((double) (size.x() * size.y() * size.z()) * configurator.getDensity());
        roomsCount = Math.min(roomsCount, configurator.getRoomsCap());
        for (int i = 0; i < 1e5; i++) {
            RoomInfo current = generator.getRandom();
            int x = (int) (Math.random() * size.x());
            int y = (int) (Math.random() * size.y());
            int z = (int) (Math.random() * size.z());
            Room room = creator.create(current, new Point(x, y, z));
            if (rooms.canPlace(room)) {
                roomsCount--;
                rooms.addRoom(room);
                if (roomsCount <= 0) {
                    return rooms;
                }
            }
        }
        return rooms;
    }
}
