package thor.core.port.output;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.UUID;

public interface ArenaManager {
    void placeArena(Point position, String worldName);

    void tpPlayerToArena(UUID playerId, Point position, String worldName);

    boolean tpPlayerFromArena(UUID playerId);
}
