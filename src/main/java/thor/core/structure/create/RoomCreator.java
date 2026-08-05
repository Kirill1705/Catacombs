package thor.core.structure.create;

import lombok.RequiredArgsConstructor;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.RoomInfo;
import thor.core.structure.Room;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.util.RandomGenerator;

@RequiredArgsConstructor
public class RoomCreator implements StructureCreator {
    private final RandomGenerator<RoomInfo> randomGenerator;

    @Override
    public Room create(Point position) {
        Converter converter = Converter.simple(position);
        return new Room(converter, randomGenerator.getRandom());
    }
}
