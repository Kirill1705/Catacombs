package thor.core.structure.manager;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.SignalType;
import thor.core.info.part.TeleportInfo;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.Teleport;

import java.util.*;

public class TeleportManager extends AbstractStructurePartManager<TeleportInfo, Teleport> implements SignalPartManager {
    private final Map<UUID, Long> lock = new HashMap<>();

    public TeleportManager() {

    }

    @Override
    protected Teleport create(TeleportInfo info, Converter converter) {
        return new Teleport(converter, info);
    }

    @Override
    public void onSignal(WorldAccessor accessor, UUID entityId, Point position, SignalType signalType) {
        if (getParts().size() <= 1) return;
        if (!canTeleport(entityId)) return;
        int teleportIdx = -1;
        for (int i = 0; i < getParts().size(); i++) {
            if (getParts().get(i).getPosition().equals(position) && signalType == getParts().get(i).getType()) {
                teleportIdx = i;
                break;
            }
        }
        if (teleportIdx == -1) return;
        teleportIdx = (teleportIdx + 1) % getParts().size();
        accessor.teleportPlayer(entityId, getParts().get(teleportIdx).getPlace(), getParts().get(teleportIdx).getDirection());
        lock.put(entityId, System.currentTimeMillis());
    }

    private boolean canTeleport(UUID entityId) {
        final int timeoutMills = 200;
        long time = System.currentTimeMillis();
        if (!lock.containsKey(entityId)) return true;
        long tpTime = lock.get(entityId);
        return time - tpTime > timeoutMills;
    }
}
