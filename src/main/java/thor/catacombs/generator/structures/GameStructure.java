package thor.catacombs.generator.structures;

import thor.catacombs.generator.Chest;
import thor.catacombs.generator.ItemGeneratorHolder;
import thor.catacombs.generator.structures.utils.GameWorldAccessor;
import thor.catacombs.generator.structures.utils.StructureLocation;
import thor.catacombs.info.block.ChestInfo;
import thor.catacombs.info.structure.interfaces.StructureInfo;
import thor.usefulUtils.utils.StructureUtils;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameStructure implements Structure {
    final StructureInfo info;
    final StructureLocation position;
    private final List<Chest> chests = new ArrayList<>();
    public GameStructure(StructureInfo info, StructureLocation position, ItemGeneratorHolder generator) {
        this.info = info;
        this.position = position;
        for (ChestInfo chestInfo: info.getChestsInfo()) {
            chests.add(new Chest(position, chestInfo, generator.getGenerator(chestInfo.getType())));
        }
    }
    @Override
    public void place(GameWorldAccessor accessor) {
        StructureUtils.place(info.getStructure(), accessor.begin(position).toLocation());
        for (Chest chest: chests) {
            chest.feelOrDeleteBlock(accessor);
        }
    }

    @Override
    public BlockPosition getPosition() {
        return position.begin();
    }

    @Override
    public Collection<Chest> getChests() {
        return chests;
    }

    @Override
    public StructureInfo getInfo() {
        return info;
    }

    @Override
    public List<ChestInfo> getChestsInfo() {
        return info.getChestsInfo();
    }

    @Override
    public org.bukkit.structure.Structure getStructure() {
        return info.getStructure();
    }

    @Override
    public BlockPosition getSize() {
        return info.getSize();
    }
}
