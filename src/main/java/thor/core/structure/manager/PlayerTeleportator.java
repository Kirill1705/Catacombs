package thor.core.structure.manager;

import java.util.List;
import java.util.UUID;

public interface PlayerTeleportator {
    void tpPlayers(List<UUID> entityIds);
}
