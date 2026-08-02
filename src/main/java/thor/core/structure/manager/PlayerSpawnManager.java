package thor.core.structure.manager;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.exception.MapNotPlacedException;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.RoomInfo;
import thor.core.info.part.PartTunnelInfo;
import thor.core.info.part.PlayerSpawnPlaceInfo;
import thor.core.port.mapping.MapPlaceOptions;
import thor.core.port.output.WorldAccessor;
import thor.core.port.output.WorldAccessorCreator;
import thor.core.structure.*;

import java.util.*;

public class PlayerSpawnManager extends AbstractStructurePartManager<PlayerSpawnPlaceInfo, PlayerSpawnNode> implements PlayerTeleportator, PlacePartManager {
    private List<PartTunnel> tunnels = new ArrayList<>();

    private WorldAccessor worldAccessor;

    public void addTunnels(List<PartTunnel> tunnels) {
        tunnels.addAll(tunnels);
    }

    @Override
    public void tpPlayers(List<UUID> entityIds) {
        if (worldAccessor == null) {
            throw new MapNotPlacedException();
        }
        addSpawnsBeforeCap(entityIds.size());
        List<PlayerSpawnNode> nodes = getSortedNodes();
        for (int i = 0; i < entityIds.size(); i++) {
            worldAccessor.teleportEntity(entityIds.get(i), nodes.get(i).getPosition(), null);
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
            getParts().add(new PlayerSpawnNode(Converter.simple(tunnels.get(i).getAttachmentPoint()), new PlayerSpawnPlaceInfo(
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
    protected PlayerSpawnNode create(PlayerSpawnPlaceInfo info, Converter converter) {
        return new PlayerSpawnNode(converter, info);
    }

    @Override
    protected Iterable<PlayerSpawnPlaceInfo> extractFromRoomInfo(RoomInfo roomInfo) {
        return roomInfo.getPlayerSpawnPlaces();
    }

    @Override
    protected Iterable<PlayerSpawnPlaceInfo> extractFromPartTunnelInfo(PartTunnelInfo partTunnelInfo) {
        return partTunnelInfo.getPlayerSpawnPlaces();
    }

    @Override
    public Optional<PlacePartResult> place(WorldAccessorCreator accessorCreator, String worldName, Point position, MapPlaceOptions options) {
        worldAccessor = accessorCreator.create(position, worldName);
        return Optional.empty();
    }
}
