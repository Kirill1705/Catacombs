package thor.catacombs.info.structure;

import org.bukkit.entity.Entity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.structure.Structure;
import thor.catacombs.info.block.ChestInfo;
import thor.catacombs.info.block.generator.BlockInfoGenerator;
import thor.catacombs.info.structure.extra.ChestLoader;
import thor.catacombs.info.structure.interfaces.StructureInfo;
import thor.usefulUtils.utils.StructureUtils;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.ImmutableBox;

import java.util.Collection;
import java.util.List;

public class GameStructureInfo implements StructureInfo {
    private final BlockPosition size;
    private final ChestLoader chestLoader;
    private final Structure structure;

    @Override
    public List<ChestInfo> getChestsInfo() {
        return chestLoader.getChests();
    }

    @Override
    public Structure getStructure() {
        return structure;
    }

    @Override
    public BlockPosition getSize() {
        return size;
    }

    public GameStructureInfo(BlockInfoGenerator generator, ChestLoader chestLoader) {
        this.chestLoader = chestLoader;
        ImmutableBox box = generator.getBox();
        this.size = box.size();
        Collection<Entity> entities = box.begin().world().getNearbyEntities(box.toBoundingBox());
        for (Entity entity: entities) {
            if (entity instanceof TextDisplay display) {
                display.remove();
            }
        }
        generator.killAllDisplays();
        this.structure = StructureUtils.saveStructure(box);
    }
}