package thor.catacombs.info.block.creators;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.info.block.BlockInfo;

public interface BlockInfoCreator<T extends BlockInfo> {
    T create(BlockPosition position, YamlConfiguration configuration, BlockLocation location);
}
