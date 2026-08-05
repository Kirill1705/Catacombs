package thor.core.structure.manager;

import lombok.RequiredArgsConstructor;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.IslandInfo;
import thor.core.info.part.ChestInfo;
import thor.core.info.part.PartTunnelInfo;
import thor.core.port.mapping.MapPlaceOptions;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.PlacePartResult;
import thor.core.structure.StructurePartPlaceInfo;
import thor.core.structure.chest.Chest;
import thor.core.structure.chest.ItemCreator;
import thor.core.structure.manager.config.PlacePartManager;

import java.util.Optional;

@RequiredArgsConstructor
public class ChestManager extends AbstractStructurePartManager<ChestInfo, Chest> implements PlacePartManager {
    private final ItemCreator itemCreator;

    @Override
    protected Chest create(ChestInfo info, StructurePartPlaceInfo converter) {
        return new Chest(converter, info, itemCreator);
    }

    @Override
    protected Iterable<ChestInfo> extractFromIslandInfo(IslandInfo roomInfo) {
        return roomInfo.getChests();
    }

    @Override
    public void place(WorldAccessor accessor) {
        for (Chest chest: getParts()) {
            accessor.placeChest(chest.getPosition(), chest.getItems(), chest.getBooks(), chest.getMaterial(), chest.getWorldName());
        }
    }
}
