package thor.core.structure;

import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.RoomInfo;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.chest.ItemCreator;

import java.util.Collection;
import java.util.List;

public class RoomImpl extends AbstractStructure implements Room{
    private final List<Exit> exits;
    private final Collection<String> tunnels;
    public RoomImpl(ItemCreator generator, Converter converter, RoomInfo info) {
        super(generator, converter, info, false);
        tunnels = info.getTunnels();
        exits = info.getExits().stream()
                .map(exitInfo -> new Exit(converter, exitInfo, tunnels, info.getSize()))
                .toList();
    }

    @Override
    public Collection<Exit> getExits() {
        return exits;
    }

    @Override
    public Collection<String> possibleTunnelsNames() {
        return tunnels;
    }

    @Override
    protected void place(WorldAccessor accessor, boolean rotated) {
        accessor.placeRoom(getTextId(), getPosition(), rotated);
        exits.forEach(exit -> exit.place(accessor));
    }
}
