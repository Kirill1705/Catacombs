package thor.core.structure.manager;

import lombok.RequiredArgsConstructor;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.info.SignalType;
import thor.core.port.output.WorldAccessor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class SignalPartManagerGroup implements SignalPartManager {
    private final List<SignalPartManager> managers;

    @Override
    public void onSignal(WorldAccessor accessor, UUID entityId, Point position, SignalType signalType) {
        for (SignalPartManager manager: managers) {
            manager.onSignal(accessor, entityId, position, signalType);
        }
    }
}
