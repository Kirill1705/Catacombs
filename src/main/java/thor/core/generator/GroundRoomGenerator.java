package thor.core.generator;

import lombok.extern.slf4j.Slf4j;
import thor.core.exception.DomainValidationException;
import thor.core.exception.RoomsNotFoundException;
import thor.core.generator.complete.GameMap;
import thor.core.info.RoomInfo;
import thor.core.structure.Room;
import thor.core.structure.create.RoomCreator;
import thor.core.util.RandomGenerator;
import thor.core.util.RandomGeneratorImpl;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.List;

@Slf4j
public class GroundRoomGenerator implements RoomGenerator{
    private final RandomGenerator<RoomInfo> rawRooms;
    private final Point size;
    private final int roomsQuantity;
    private final RoomCreator creator;

    public GroundRoomGenerator(List<RoomInfo> roomInfos, Point size, int roomsQuantity, RoomCreator creator) {
        this.creator = creator;
        if (roomInfos.isEmpty()) {
            throw new RoomsNotFoundException();
        }
        if (!size.more(new Point(0, 0, 0))) {
            throw new DomainValidationException(size);
        }
        if (roomsQuantity < 0) {
            throw new DomainValidationException(roomsQuantity);
        }
        this.rawRooms = new RandomGeneratorImpl<>(roomInfos);
        this.size = size;
        this.roomsQuantity = roomsQuantity;
    }

    @Override
    public GameMap generate() {
        GameMap map = new GameMap(size);
        log.info("Starting ground rooms generator. {} rooms required", roomsQuantity);
        int roomsCount = 0;
        for (int i = 0; i < 1e5; i++) {
            RoomInfo current = rawRooms.getRandom();
            int x = (int) (Math.random() * size.x());
            int y = (int) (Math.random() * size.y());
            int z = (int) (Math.random() * size.z());
            Room room = creator.create(new Point(x, y, z), current);
            if (map.addRoom(room)) {
                roomsCount++;
                if (roomsCount >= roomsQuantity) {
                    log.info("Rooms generated successfully");
                    return map;
                }
            }
        }
        log.warn("Generated only {} rooms", roomsCount);
        return map;
    }
}
