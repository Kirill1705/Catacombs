package thor.core.generator;

import lombok.extern.slf4j.Slf4j;
import thor.core.exception.DomainValidationException;
import thor.core.generator.complete.GameMap;
import thor.core.structure.Structure;
import thor.core.structure.create.StructureCreator;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

@Slf4j
public class IslandGenerator implements Generator {
    private final int roomsQuantity;
    private final StructureCreator creator;

    public IslandGenerator(int roomsQuantity, StructureCreator creator) {
        this.creator = creator;
        if (roomsQuantity < 0) {
            throw new DomainValidationException(roomsQuantity);
        }
        this.roomsQuantity = roomsQuantity;
    }

    @Override
    public void generate(GameMap map) {
        Point size = map.getSize();
        log.info("Starting ground rooms generator. {} rooms required", roomsQuantity);
        int roomsCount = 0;
        for (int i = 0; i < 1e5; i++) {
            int x = (int) (Math.random() * size.x());
            int y = (int) (Math.random() * size.y());
            int z = (int) (Math.random() * size.z());
            Structure room = creator.create(new Point(x, y, z));
            if (map.canAddByOverlaps(room)) {
                map.addStructure(room);
                roomsCount++;
                if (roomsCount >= roomsQuantity) {
                    log.info("Rooms generated successfully");
                    return;
                }
            }
        }
        log.warn("Generated only {} rooms", roomsCount);
    }
}
