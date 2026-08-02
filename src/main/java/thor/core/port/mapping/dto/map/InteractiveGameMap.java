package thor.core.port.mapping.dto.map;

import thor.core.structure.manager.*;

import java.util.List;
import java.util.UUID;

public record InteractiveGameMap(
        UUID id,
        List<SignalPartManager> signalPartManagers,
        PlayerTeleportator playerSpawnManager,
        ArenaTeleportator arenaTeleportator,
        List<PlacePartManager> placePartManagers
) {
}
