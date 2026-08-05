package thor.core.structure.manager;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.info.SignalType;
import thor.core.port.output.WorldAccessor;

import java.util.UUID;

public interface SignalPartManager {
    void onSignal(WorldAccessor accessor, UUID entityId, Point position, SignalType signalType, String worldName);
}
