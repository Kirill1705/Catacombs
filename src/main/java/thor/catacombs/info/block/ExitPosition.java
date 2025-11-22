package thor.catacombs.info.block;

import org.bukkit.configuration.file.YamlConfiguration;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public class ExitPosition extends BlockInfo{
    private final YamlConfiguration config;
    public ExitPosition(BlockPosition position, YamlConfiguration config) {
        super(position);
        this.config = config;
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
