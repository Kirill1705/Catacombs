package thor.core.structure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import ru.vikhrenko.serverUtils.utils.dataStructures.Boxes;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableLocation;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.port.mapping.MapPlaceOptions;
import thor.core.port.output.StructureManager;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.manager.config.PlacePartManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CatacombsPlaceStructureVisitor implements StructureVisitor, PlacePartManager {
    private final StructureManager structureManager;
    private final Point size;
    private final MapPlaceOptions options;
    private final ImmutableLocation location;
    private final Material material;

    private final List<Room> rooms = new ArrayList<>();
    private final List<PartTunnel> verticalTunnels = new ArrayList<>();
    @Getter
    private final List<PartTunnel> horizontalTunnels = new ArrayList<>();
    private final List<Island> waterIslands = new ArrayList<>();

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
    public void visit(Island island) {
        waterIslands.add(island);
    }

    @Override
    public void place(WorldAccessor accessor) {
        if (options.isFillBedrock()) {
            fillBedrock(accessor, location.worldName());
        }
        if (options.isFillStone()) {
            fillStone(accessor, material);
        }
        accessor.killEntities(Boxes.fromBeginAndSize(location.position(), size), location.worldName());
        place(accessor, rooms);
        place(accessor, verticalTunnels);
        place(accessor, horizontalTunnels);
        place(accessor, waterIslands);
        for (PartTunnel partTunnel: verticalTunnels) {
            partTunnel.afterPlace(accessor, location);
        }
    }

    private <T extends Structure> void place(WorldAccessor accessor, List<T> structures) {
        structures.forEach(structure -> structure.place(accessor, structureManager, location));
    }

    private void fillBedrock(WorldAccessor accessor, String worldName) {
        fill(accessor, -1, -1, -1, size.x(), size.y(), -1, Material.BEDROCK, worldName);
        fill(accessor, -1, -1, -1, size.x(), -1, size.z(), Material.BEDROCK, worldName);
        fill(accessor, -1, -1, -1, -1, size.y(), size.z(), Material.BEDROCK, worldName);
        fill(accessor, -1, size.y(), -1, size.x(), size.y(), size.z(), Material.BEDROCK, worldName);
        fill(accessor, size.x(), -1, -1, size.x(), size.y(), size.z(), Material.BEDROCK, worldName);
        fill(accessor, -1, -1, size.z(), size.x(), size.y(), size.z(), Material.BEDROCK, worldName);
    }

    private void fillStone(WorldAccessor accessor, Material material) {
        fill(accessor, 0, 0, 0, size.x() - 1, size.y() - 1, size.z() - 1, material, location.worldName());
    }

    private void fill(WorldAccessor accessor, int x0, int y0, int z0, int x, int y, int z, Material material, String worldName) {
        accessor.fill(location.position().x() + x0, location.position().y() + y0, location.position().z() + z0, location.position().x() + x, location.position().y() + y, location.position().z() + z, material, worldName);
    }
}
