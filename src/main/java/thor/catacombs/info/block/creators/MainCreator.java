package thor.catacombs.info.block.creators;

import org.bukkit.configuration.file.YamlConfiguration;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.block.MainInfo;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public record MainCreator(AttributeRegistry registry) implements BlockInfoCreator<MainInfo>  {
    @Override
    public MainInfo create(BlockPosition position, YamlConfiguration configuration, BlockLocation location) {
        return new MainInfo(position, configuration, registry);
    }
}
