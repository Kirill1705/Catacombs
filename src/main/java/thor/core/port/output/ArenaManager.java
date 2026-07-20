package thor.core.port.output;

import thor.core.port.mapping.dto.PositionDto;

import java.util.UUID;

public interface ArenaManager {
    void placeArena(PositionDto position, String worldName);

    void tpPlayerToArena(UUID playerId, PositionDto position, String worldName);

    boolean tpPlayerFromArena(UUID playerId);
}
