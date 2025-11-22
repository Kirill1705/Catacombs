package thor.catacombs.info.block.creators;

import org.bukkit.configuration.file.YamlConfiguration;
import thor.catacombs.info.block.ExitPosition;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public class ExitPositionCreator implements BlockInfoCreator<ExitPosition> {
    @Override
    public ExitPosition create(BlockPosition position, YamlConfiguration configuration, BlockLocation location) {
        return new ExitPosition(position, configuration);
    }
}
