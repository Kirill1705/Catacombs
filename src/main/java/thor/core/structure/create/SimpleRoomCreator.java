package thor.core.structure.create;

import thor.core.generator.tunnel.convert.ConverterImpl;
import thor.core.info.RoomInfo;
import thor.core.structure.Room;
import thor.core.structure.RoomImpl;
import thor.core.structure.chest.ItemCreator;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public record SimpleRoomCreator(ItemCreator items) implements RoomCreator {
    @Override
    public Room create(Point position, RoomInfo info) {
        return new RoomImpl(items, new ConverterImpl(position, position.add(info.getSize()).subtract(new Point(1, 1, 1))), info);
    }
}
