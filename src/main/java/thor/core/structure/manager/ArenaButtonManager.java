package thor.core.structure.manager;

import org.bukkit.Material;
import ru.vikhrenko.serverUtils.utils.dataStructures.*;
import thor.core.info.IslandInfo;
import thor.core.info.SignalType;
import thor.core.info.part.ArenaButtonInfo;
import thor.core.port.mapping.MapPlaceOptions;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.ArenaButton;
import thor.core.structure.StructurePartPlaceInfo;
import thor.core.structure.manager.config.ArenaConfig;

import java.util.*;

public class ArenaButtonManager extends AbstractStructurePartManager<ArenaButtonInfo, ArenaButton> implements SignalPartManager, ArenaTeleportator {
    private final Map<UUID, ImmutableLocation> originPositionMap = new HashMap<>();
    private final ArenaConfig arenaConfig;
    private final ImmutableWorldBox arenaBox;

    public ArenaButtonManager(ArenaConfig arenaConfig, Point relativeMapArenaPosition, ImmutableLocation location, WorldAccessor accessor, MapPlaceOptions options) {
        this.arenaConfig = arenaConfig;
        this.arenaBox = place(accessor, new ImmutableLocation(location.position().add(relativeMapArenaPosition).add(new Point(2, 2, 2)), location.worldName()), options);
    }

    @Override
    protected ArenaButton create(ArenaButtonInfo info, StructurePartPlaceInfo placeInfo) {
        return new ArenaButton(placeInfo, info);
    }

    @Override
    protected Iterable<ArenaButtonInfo> extractFromIslandInfo(IslandInfo roomInfo) {
        return roomInfo.getArenaButtons();
    }

    @Override
    public void onSignal(WorldAccessor accessor, UUID entityId, Point position, SignalType signalType, String worldName) {
        if (arenaConfig.getButtonPosition().add(arenaBox.box().begin()).equals(position) && signalType == SignalType.BUTTON && originPositionMap.containsKey(entityId)) {
            ImmutableLocation location = originPositionMap.get(entityId);
            accessor.teleportEntity(entityId, location.position(), null, arenaBox.worldName());
            originPositionMap.remove(entityId);
            return;
        }
        for (ArenaButton arenaButton: findPart(position, worldName, arenaButton -> arenaButton.getSignalType() == signalType)) {
            teleport(entityId, accessor);
            originPositionMap.put(entityId, new ImmutableLocation(arenaButton.getBackPosition(), arenaBox.worldName()));
        }
    }

    @Override
    public void teleport(UUID entityId, WorldAccessor accessor) {
        ImmutableBox box = arenaBox.box();
        accessor.teleportEntityInRandomPlaceInBox(box, entityId, arenaBox.worldName());
    }

    public Point getArenaMaxOffset() {
        return arenaConfig.getSize().add(new Point(2, 2, 2));
    }

    private ImmutableWorldBox place(WorldAccessor accessor, ImmutableLocation location, MapPlaceOptions options) {
        ImmutableWorldBox box = new ImmutableWorldBox(Boxes.fromBeginAndSize(location.position(), arenaConfig.getSize()), location.worldName());
        if (options.isFillBedrock()) {
            fillBedrock(accessor, box);
        }
        accessor.placeStructure(arenaConfig.getArenaPath(), location.position(), false, location.worldName());
        return box;
    }

    private void fillBedrock(WorldAccessor accessor, ImmutableWorldBox box) {
        int x0 = box.box().begin().x() - 1;
        int y0 = box.box().begin().y() - 1;
        int z0 = box.box().begin().z() - 1;
        int y = box.box().end().y() - 1;
        int x = box.box().end().x() - 1;
        int z = box.box().end().z() - 1;
        accessor.fill(x0, y0, z0, x, y, z0, Material.BEDROCK, box.worldName());
        accessor.fill(x0, y0, z0, x, y0, z, Material.BEDROCK, box.worldName());
        accessor.fill(x0, y0, z0, x0, y, z, Material.BEDROCK, box.worldName());
        accessor.fill(x0, y0, z, x, y, z, Material.BEDROCK, box.worldName());
        accessor.fill(x0, y, z0, x, y, z, Material.BEDROCK, box.worldName());
        accessor.fill(x, y0, z0, x, y, z, Material.BEDROCK, box.worldName());
    }
}
