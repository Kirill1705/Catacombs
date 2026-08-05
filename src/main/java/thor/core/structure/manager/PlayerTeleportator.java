package thor.core.structure.manager;

import thor.core.port.output.WorldAccessor;

import java.util.List;
import java.util.UUID;

public interface PlayerTeleportator {
    void tpPlayers(List<UUID> entityIds, WorldAccessor accessor);
}
