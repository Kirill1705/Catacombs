package thor.core.structure.manager;

import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableLocation;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.exception.MapNotPlacedException;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.IslandInfo;
import thor.core.info.part.PartTunnelInfo;
import thor.core.info.part.PlayerSpawnPlaceInfo;
import thor.core.port.mapping.MapPlaceOptions;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.*;

import java.util.*;

public class PlayerSpawnManager extends AbstractStructurePartManager<PlayerSpawnPlaceInfo, PlayerSpawnNode> implements PlayerTeleportator {
    private final List<PartTunnel> tunnels = new ArrayList<>();

    private final ImmutableLocation location;

    public PlayerSpawnManager(ImmutableLocation location) {
        this.location = location;
    }

    public void addTunnels(List<PartTunnel> tunnels) {
        this.tunnels.addAll(tunnels);
    }

    @Override
    public void tpPlayers(List<UUID> entityIds, WorldAccessor accessor) {
        addSpawnsBeforeCap(entityIds.size());
        List<PlayerSpawnNode> nodes = getSortedNodes();
        for (int i = 0; i < entityIds.size(); i++) {
            accessor.teleportEntity(entityIds.get(i), nodes.get(i).getPosition(), null, location.worldName());
        }
    }

    private void addSpawnsBeforeCap(int capacity) {
        int count = capacity - getParts().size();
        if (count <= 0) return;
        if (count > tunnels.size()) {
            throw new IllegalStateException();
        }
        Collections.shuffle(tunnels);
        for (int i = 0; i < count; i++) {
            StructurePartPlaceInfo placeInfo = new StructurePartPlaceInfo(Converter.simple(tunnels.get(i).getAttachmentPoint()), location.position(), location.worldName());
            getParts().add(new PlayerSpawnNode(placeInfo, new PlayerSpawnPlaceInfo(
                    PlayerSpawnPriority.lowest(),
                    new Point(0, 0, 0)
            )));
        }
    }

    public List<PlayerSpawnNode> getSortedNodes() {
        return getParts().stream()
                .sorted(Comparator.comparing(PlayerSpawnNode::getPriority))
                .toList();
    }

    @Override
    protected PlayerSpawnNode create(PlayerSpawnPlaceInfo info, StructurePartPlaceInfo placeInfo) {
        return new PlayerSpawnNode(placeInfo, info);
    }

    @Override
    protected Iterable<PlayerSpawnPlaceInfo> extractFromIslandInfo(IslandInfo roomInfo) {
        return roomInfo.getPlayerSpawnPlaces();
    }
}
