package thor.core.structure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import ru.vikhrenko.serverUtils.utils.dataStructures.Boxes;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.port.mapping.MapPlaceOptions;
import thor.core.port.output.StructureManager;
import thor.core.port.output.WorldAccessor;
import thor.core.port.output.WorldAccessorCreator;
import thor.core.structure.manager.PlacePartManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CatacombsPlaceStructureVisitor implements StructureVisitor, PlacePartManager {
    private final StructureManager structureManager;
    private final Point size;

    private final List<Room> rooms = new ArrayList<>();
    private final List<PartTunnel> verticalTunnels = new ArrayList<>();
    @Getter
    private final List<PartTunnel> horizontalTunnels = new ArrayList<>();

    @Override
    public void visit(Room room) {
        rooms.add(room);
    }

    @Override
    public void visit(PartTunnel partTunnel) {
        if (partTunnel.isVertical()) {
            verticalTunnels.add(partTunnel);
        }
        else {
            horizontalTunnels.add(partTunnel);
        }
    }

    @Override
    public Optional<PlacePartResult> place(WorldAccessorCreator accessorCreator, String worldName, Point position, MapPlaceOptions options) {
        WorldAccessor accessor = accessorCreator.create(position, worldName);
        if (options.isFillBedrock()) {
            fillBedrock(accessor);
        }
        if (options.isFillStone()) {
            fillStone(accessor);
        }
        accessor.killEntities(Boxes.fromBeginAndSize(new Point(0, 0, 0), size));
        place(accessor, rooms);
        place(accessor, verticalTunnels);
        place(accessor, horizontalTunnels);
        for (PartTunnel partTunnel: verticalTunnels) {
            partTunnel.afterPlace(accessor);
        }
        return Optional.of(new PlacePartResult(Boxes.fromBeginAndSize(new Point(0, 0, 0), size), worldName));
    }

    private <T extends Structure> void place(WorldAccessor accessor, List<T> structures) {
        structures.forEach(structure -> structure.place(accessor, structureManager));
    }

    private void fillBedrock(WorldAccessor accessor) {
        accessor.fill(-1, -1, -1, size.x(), size.y(), -1, Material.BEDROCK);
        accessor.fill(-1, -1, -1, size.x(), -1, size.z(), Material.BEDROCK);
        accessor.fill(-1, -1, -1, -1, size.y(), size.z(), Material.BEDROCK);
        accessor.fill(-1, size.y(), size.z(), size.x(), size.y(), size.z(), Material.BEDROCK);
        accessor.fill(size.x(), size.y(), -1, size.x(), size.y(), size.z(), Material.BEDROCK);
        accessor.fill(size.x(), -1, size.z(), size.x(), size.y(), size.z(), Material.BEDROCK);
    }

    private void fillStone(WorldAccessor accessor) {
        accessor.fill(0, 0, 0, size.x() - 1, size.y() - 1, size.z() - 1, Material.STONE);
    }
}
