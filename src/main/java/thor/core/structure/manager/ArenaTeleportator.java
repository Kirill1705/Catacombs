package thor.core.structure.manager;

import thor.core.port.output.WorldAccessor;

import java.util.UUID;

public interface ArenaTeleportator {
    void teleport(UUID entityId, WorldAccessor accessor);
}
