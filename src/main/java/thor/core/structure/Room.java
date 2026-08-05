package thor.core.structure;

import lombok.Getter;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableLocation;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.RoomInfo;
import thor.core.port.output.StructureManager;
import thor.core.port.output.WorldAccessor;

import java.util.Collection;
import java.util.List;

public class Room extends AbstractStructure {
    private final List<Exit> exits;
    private final Collection<String> tunnels;
    @Getter
    private final RoomInfo roomInfo;

    public Room(Converter converter, RoomInfo info) {
        super(converter, info, false);
        tunnels = info.getTunnels();
        exits = info.getExits().stream()
                .map(exitInfo -> new Exit(converter, exitInfo, tunnels, info.getSize()))
                .toList();
        this.roomInfo = info;
    }

    public Collection<Exit> getExits() {
        return exits;
    }

    @Override
    public void place(WorldAccessor accessor, StructureManager structureManager, ImmutableLocation location) {
        String path = structureManager.getRoomStructurePath(getTextId());
        accessor.placeStructure(path, getPosition().add(location.position()), isRotated(), location.worldName());
        exits.forEach(exit -> exit.place(accessor, location));
    }

    @Override
    public void accept(StructureVisitor visitor) {
        visitor.visit(this);
    }
}
