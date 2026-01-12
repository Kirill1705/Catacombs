package thor.catacombs.info.structure.extra;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import thor.catacombs.info.attributes.AttributeHolderType;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.block.ChestInfo;
import thor.catacombs.info.block.creators.ChestCreator;
import thor.catacombs.info.block.generator.BlockInfoGenerator;
import thor.usefulUtils.utils.dataStructures.BlockLocation;

import java.util.ArrayList;
import java.util.List;

public class GameChestLoader implements ChestLoader {
    private final List<ChestInfo> chests;
    private final AttributeRegistry registry;
    private List<ChestInfo> loadAllChests(Iterable<Block> structure, BlockLocation begin) {
        List<ChestInfo> chests = new ArrayList<>();
        structure.forEach(block -> {
            BlockLocation location = new BlockLocation(block.getLocation());
            if (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST || block.getType() == Material.BARREL) {
                chests.add(new ChestCreator(registry).create(location.subtract(begin), new YamlConfiguration(), location));
            }
        });
        return chests;
    }
    public GameChestLoader(BlockInfoGenerator generator, AttributeRegistry registry) {
        List<ChestInfo> chests = generator.getBlockInfo(AttributeHolderType.CHEST.getName(), new ChestCreator(registry));
        this.registry = registry;
        if (chests.isEmpty()) {
            this.chests = loadAllChests(generator.getBox().blockIterable(), generator.getBegin());
        }
        else {
            this.chests = chests;
        }
    }
    @Override
    public List<ChestInfo> getChests() {
        return chests;
    }
}
