package thor.catacombs.info.block.creators;

import org.bukkit.configuration.file.YamlConfiguration;
import thor.catacombs.info.block.PlayerSpawnInfo;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public record PlayerSpawnInfoCreator() implements BlockInfoCreator<PlayerSpawnInfo> {
    @Override
    public PlayerSpawnInfo create(BlockPosition position, YamlConfiguration configuration, BlockLocation location) {
        return new PlayerSpawnInfo(position);
    }
}
