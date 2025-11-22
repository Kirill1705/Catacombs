package thor.catacombs.info.block.creators;

import org.bukkit.block.Container;
import org.bukkit.configuration.file.YamlConfiguration;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.block.ChestInfo;

import java.awt.*;

public record ChestCreator(AttributeRegistry registry) implements BlockInfoCreator<ChestInfo>{
    @Override
    public ChestInfo create(BlockPosition position, YamlConfiguration configuration, BlockLocation location) {
        return new ChestInfo(configuration, position, location.toLocation().getBlock(), registry);
    }
}
