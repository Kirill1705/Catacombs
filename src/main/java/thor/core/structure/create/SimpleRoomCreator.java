package thor.core.structure.create;

import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.RoomInfo;
import thor.core.structure.Room;
import thor.core.structure.RoomImpl;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public record SimpleRoomCreator() implements RoomCreator {
    @Override
    public Room create(Point position, RoomInfo info) {
        Converter converter = Converter.simple(position);
        return new RoomImpl(converter, info);
    }
}
