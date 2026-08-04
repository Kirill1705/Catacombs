package thor.core.structure.manager;

import lombok.RequiredArgsConstructor;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.RoomInfo;
import thor.core.info.part.ChestInfo;
import thor.core.info.part.PartTunnelInfo;
import thor.core.port.mapping.MapPlaceOptions;
import thor.core.port.output.WorldAccessor;
import thor.core.port.output.WorldAccessorCreator;
import thor.core.structure.PlacePartResult;
import thor.core.structure.chest.Chest;
import thor.core.structure.chest.ItemCreator;

import java.util.Optional;

@RequiredArgsConstructor
public class ChestManager extends AbstractStructurePartManager<ChestInfo, Chest> implements PlacePartManager {
    private final ItemCreator itemCreator;

    @Override
    protected Chest create(ChestInfo info, Converter converter) {
        return new Chest(converter, info, itemCreator);
    }

    @Override
    protected Iterable<ChestInfo> extractFromRoomInfo(RoomInfo roomInfo) {
        return roomInfo.getChests();
    }

    @Override
    protected Iterable<ChestInfo> extractFromPartTunnelInfo(PartTunnelInfo partTunnelInfo) {
        return partTunnelInfo.getChests();
    }

    @Override
    public Optional<PlacePartResult> place(WorldAccessorCreator accessorCreator, String worldName, Point position, MapPlaceOptions options) {
        WorldAccessor accessor = accessorCreator.create(position, worldName);
        for (Chest chest: getParts()) {
            accessor.placeChest(chest.getPosition(), chest.getItems(), chest.getBooks(), chest.getMaterial());
        }
        return Optional.empty();
    }
}
