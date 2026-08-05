package thor.core.structure.manager;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.port.output.WorldAccessor;

import java.util.UUID;

public interface PortalHandler {
    boolean tryTeleport(WorldAccessor accessor, UUID entityId, Point position, String sourceWorld, String destWorld);
}
