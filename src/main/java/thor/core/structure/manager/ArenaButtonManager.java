package thor.core.structure.manager;

import org.bukkit.Material;
import ru.vikhrenko.serverUtils.utils.dataStructures.Boxes;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.RoomInfo;
import thor.core.info.SignalType;
import thor.core.info.part.ArenaButtonInfo;
import thor.core.info.part.PartTunnelInfo;
import thor.core.port.mapping.MapPlaceOptions;
import thor.core.port.output.WorldAccessor;
import thor.core.port.output.WorldAccessorCreator;
import thor.core.structure.ArenaButton;
import thor.core.structure.PlacePartResult;
import thor.core.structure.manager.config.ArenaConfig;

import java.util.*;

public class ArenaButtonManager extends AbstractStructurePartManager<ArenaButtonInfo, ArenaButton> implements SignalPartManager, PlacePartManager, ArenaTeleportator {
    private final Map<UUID, Point> originPositionMap = new HashMap<>();
    private final ArenaConfig arenaConfig;
    private final Point arenaPosition;

    private WorldAccessor arenaAccessor;

    public ArenaButtonManager(ArenaConfig arenaConfig, Point arenaPosition) {
        this.arenaConfig = arenaConfig;
        this.arenaPosition = arenaPosition;
    }

    @Override
    protected ArenaButton create(ArenaButtonInfo info, Converter converter) {
        return new ArenaButton(converter, info);
    }

    @Override
    protected Iterable<ArenaButtonInfo> extractFromRoomInfo(RoomInfo roomInfo) {
        return roomInfo.getArenaButtons();
    }

    @Override
    protected Iterable<ArenaButtonInfo> extractFromPartTunnelInfo(PartTunnelInfo partTunnelInfo) {
        return List.of();
    }

    @Override
    public void onSignal(WorldAccessor accessor, UUID entityId, Point position, SignalType signalType) {
        if (arenaPosition.add(arenaConfig.getButtonPosition()).equals(position) && signalType == SignalType.BUTTON && originPositionMap.containsKey(entityId)) {
            accessor.teleportEntity(entityId, originPositionMap.get(entityId), null);
            originPositionMap.remove(entityId);
            return;
        }
        for (ArenaButton arenaButton: getParts()) {
            if (arenaButton.getPosition().equals(position) && arenaButton.getSignalType() == signalType) {
                teleport(entityId);
                originPositionMap.put(entityId, arenaButton.getBackPosition());
            }
        }
    }

    @Override
    public Optional<PlacePartResult> place(WorldAccessorCreator accessorCreator, String worldName, Point position, MapPlaceOptions options) {
        arenaAccessor = accessorCreator.create(position, worldName);
        if (options.isFillBedrock()) {
            fillBedrock(getArenaBox(), arenaAccessor);
        }
        arenaAccessor.placeStructure(arenaConfig.getArenaPath(), arenaPosition, false);
        return Optional.of(new PlacePartResult(getArenaBox(), worldName));
    }

    public void teleport(UUID entityId) {
        if (arenaAccessor == null) {
            throw new IllegalStateException("Arena is not placed yet. Cant teleport");
        }
        ImmutableBox box = getArenaBox();
        arenaAccessor.teleportEntityInRandomPlaceInBox(box, entityId);
    }

    private ImmutableBox getArenaBox() {
        return Boxes.fromBeginAndSize(arenaPosition, arenaConfig.getSize());
    }

    private void fillBedrock(ImmutableBox arena, WorldAccessor accessor) {
        accessor.fill(arena.begin().x() - 1, arena.begin().y() - 1, arena.begin().z() - 1, arena.end().x() - 1, arena.end().y() - 1, arena.begin().z() - 1, Material.BEDROCK);
        accessor.fill(arena.begin().x() - 1, arena.begin().y() - 1, arena.begin().z() - 1, arena.end().x() - 1, arena.begin().y() - 1, arena.end().z() - 1, Material.BEDROCK);
        accessor.fill(arena.begin().x() - 1, arena.begin().y() - 1, arena.begin().z() - 1, arena.begin().x() - 1, arena.end().y() - 1, arena.end().z() - 1, Material.BEDROCK);
        accessor.fill(arena.begin().x() - 1, arena.begin().y() - 1, arena.end().z() - 1, arena.end().x() - 1, arena.end().y() - 1, arena.end().z() - 1, Material.BEDROCK);
        accessor.fill(arena.begin().x() - 1, arena.end().y() - 1, arena.begin().z() - 1, arena.end().x() - 1, arena.end().y() - 1, arena.end().z() - 1, Material.BEDROCK);
        accessor.fill(arena.end().x() - 1, arena.begin().y() - 1, arena.begin().z() - 1, arena.end().x() - 1, arena.end().y() - 1, arena.end().z() - 1, Material.BEDROCK);
    }
}
