package thor.core.structure.manager.config;

import org.bukkit.structure.Structure;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public interface ArenaConfig {
    Point getButtonPosition();

    String getArenaPath();

    Point getSize();
}
