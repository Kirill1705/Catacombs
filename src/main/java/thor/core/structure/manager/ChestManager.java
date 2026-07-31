package thor.core.structure.manager;

import lombok.RequiredArgsConstructor;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.part.ChestInfo;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.chest.Chest;
import thor.core.structure.chest.ItemCreator;

@RequiredArgsConstructor
public class ChestManager extends AbstractStructurePartManager<ChestInfo, Chest> {
    private final ItemCreator itemCreator;

    @Override
    protected Chest create(ChestInfo info, Converter converter) {
        return new Chest(converter, info, itemCreator);
    }

    public void place(WorldAccessor accessor) {
        for (Chest chest: getParts()) {
            accessor.placeChest(chest.getPosition(), chest.getItems(), chest.getBooks(), chest.getMaterial());
        }
    }
}
