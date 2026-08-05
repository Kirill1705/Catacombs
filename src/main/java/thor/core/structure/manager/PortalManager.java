package thor.core.structure.manager;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.info.IslandInfo;
import thor.core.info.part.Dimension;
import thor.core.info.part.PortalInfo;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.Portal;
import thor.core.structure.StructurePartPlaceInfo;

import java.util.*;
import java.util.function.Function;

public class PortalManager extends AbstractStructurePartManager<PortalInfo, Portal> implements PortalHandler {
    private final String mainWorldName;
    private final Map<Dimension, String> dimensions = new HashMap<>();

    private final List<BindedPortals> portals = new ArrayList<>();

    public PortalManager(String mainWorldName, String waterWorld) {
        this.mainWorldName = mainWorldName;
        dimensions.put(Dimension.WATER, waterWorld);
    }

    @Override
    protected Portal create(PortalInfo info, StructurePartPlaceInfo placeInfo) {
        return new Portal(placeInfo, info);
    }

    @Override
    protected Iterable<PortalInfo> extractFromIslandInfo(IslandInfo roomInfo) {
        return roomInfo.getPortals();
    }

    @Override
    public boolean tryTeleport(WorldAccessor accessor, UUID entityId, Point position, String sourceWorld, String destWorld) {
        Portal binded = findBinded(position, sourceWorld);
        if (binded != null) {
            teleport(accessor, entityId, binded);
            return true;
        }
        Portal portal = getParts().stream()
                .filter(candidate -> candidate.getWorldName().equals(sourceWorld) && candidate.getBlocks().contains(position))
                .findAny()
                .orElse(null);
        if (portal == null) return false;

        if (sourceWorld.equals(mainWorldName)) {
            String destWorldName = dimensions.get(portal.getDimension());
            if (destWorld == null) {
                teleportBack(accessor, entityId, portal);
                return true;
            }
            Portal dest = getParts().stream()
                    .filter(candidate -> candidate.getWorldName().equals(destWorldName))
                    .findAny()
                    .orElse(null);
            if (dest == null) {
                teleportBack(accessor, entityId, portal);
                return true;
            }
            teleport(accessor, entityId, dest);
            portals.add(new BindedPortals(portal, dest));
        }
        else {
            Portal dest = getParts().stream()
                    .filter(candidate -> candidate.getWorldName().equals(mainWorldName))
                    .findAny()
                    .orElse(null);
            if (dest == null) {
                teleportBack(accessor, entityId, portal);
                return true;
            }
            teleport(accessor, entityId, dest);
            portals.add(new BindedPortals(dest, portal));
        }
        return true;
    }

    private void teleportBack(WorldAccessor accessor, UUID entityId, Portal portal) {
        accessor.teleportEntity(entityId, portal.getBackPosition(), null, portal.getWorldName());
    }

    private void teleport(WorldAccessor accessor, UUID entityId, Portal portal) {
        accessor.teleportEntity(entityId, portal.getPosition(), null, portal.getWorldName());
    }

    private Portal findBinded(Point position, String worldName) {
        Function<BindedPortals, Portal> extractor = worldName.equals(mainWorldName) ? BindedPortals::main : BindedPortals::second;
        Function<BindedPortals, Portal> mapper = worldName.equals(mainWorldName) ? BindedPortals::second : BindedPortals::main;
        return portals.stream()
                .filter(bindedPortals -> {
                    Portal candidate = extractor.apply(bindedPortals);
                    return candidate.getWorldName().equals(worldName) && candidate.getBlocks().contains(position);
                })
                .map(mapper)
                .findAny()
                .orElse(null);
    }

    private record BindedPortals(Portal main, Portal second) {}
}
