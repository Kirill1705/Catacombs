package thor.catacombs.generator.structures;

import org.bukkit.Location;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.generator.Chest;
import thor.catacombs.generator.ItemGeneratorHolder;
import thor.catacombs.info.block.ChestInfo;
import thor.catacombs.info.structure.interfaces.StructureInfo;
import thor.usefulUtils.utils.StructureUtils;

import java.util.*;

public class GameStructure implements Structure {
    final StructureInfo info;
    final BlockPosition position;
    private final List<Chest> chests = new ArrayList<>();
    public GameStructure(StructureInfo info, BlockPosition position, ItemGeneratorHolder generator) {
        this.info = info;
        this.position = position;
        for (ChestInfo chestInfo: info.getChestsInfo()) {
            chests.add(new Chest(position, chestInfo, generator.getGenerator(chestInfo.getType())));
        }
    }
    @Override
    public void place(BlockLocation location) {
        StructureUtils.place(info.getStructure(), getLocation(location).toLocation());
        for (Chest chest: chests) {
            chest.feelOrDeleteBlock(location);
        }
    }

    @Override
    public BlockPosition getPosition() {
        return position;
    }

    @Override
    public BlockLocation getLocation(BlockLocation begin) {
        return begin.add(position);
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
