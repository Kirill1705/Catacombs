package thor.core.port.mapping.dto.map;

import thor.core.structure.manager.*;

import java.util.List;
import java.util.UUID;

public record InteractiveGameMap(
        List<SignalPartManager> signalPartManagers,
        PlayerTeleportator playerSpawnManager,
        ArenaTeleportator arenaTeleportator,
        PortalHandler portalHandler
) {

}
