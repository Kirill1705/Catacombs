package thor.core.structure.manager;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.SignalType;
import thor.core.info.part.TeleportInfo;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.Teleport;

import java.util.*;

public class TeleportManager extends AbstractStructurePartManager<TeleportInfo, Teleport>{
    private final Map<UUID, Long> lock = new HashMap<>();

    public TeleportManager(List<Teleport> teleports) {
        super(teleports);
    }

    public TeleportManager() {

    }

    @Override
    protected Teleport create(TeleportInfo info, Converter converter) {
        return new Teleport(converter, info);
    }

    public void tryToTeleportPlayer(WorldAccessor accessor, UUID playerId, Point position, SignalType signalType) {
        if (getParts().size() <= 1) return;
        if (!canTeleport(playerId)) return;
        int teleportIdx = -1;
        for (int i = 0; i < getParts().size(); i++) {
            if (getParts().get(i).getPosition().equals(position) && signalType == getParts().get(i).getType()) {
                teleportIdx = i;
                break;
            }
        }
        if (teleportIdx == -1) return;
        teleportIdx = (teleportIdx + 1) % getParts().size();
        accessor.teleportPlayer(playerId, getParts().get(teleportIdx).getPlace(), getParts().get(teleportIdx).getDirection());
        lock.put(playerId, System.currentTimeMillis());
    }

    boolean canTeleport(UUID entityId) {
        final int timeoutMills = 200;
        long time = System.currentTimeMillis();
        if (!lock.containsKey(entityId)) return true;
        long tpTime = lock.get(entityId);
        if (time - tpTime <= timeoutMills) return false;
        return true;
    }
}
