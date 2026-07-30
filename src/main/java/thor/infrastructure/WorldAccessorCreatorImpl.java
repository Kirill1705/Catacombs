package thor.infrastructure;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import thor.core.port.output.WorldAccessor;
import thor.core.port.output.WorldAccessorCreator;
import ru.vikhrenko.serverUtils.utils.dataStructures.BlockLocations;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.nio.file.Path;

@RequiredArgsConstructor
public class WorldAccessorCreatorImpl implements WorldAccessorCreator {
    @Override
    public WorldAccessor create(Point position, String worldName) {
        return new WorldAccessorImpl(BlockLocations.fromPointAndWorld(position, Bukkit.getWorld(worldName)));
    }
}
