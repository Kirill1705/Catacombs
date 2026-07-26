package thor.core.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import thor.core.generator.complete.GameMap;
import thor.core.port.output.StructureManager;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.PartTunnel;
import thor.core.structure.Room;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.Collection;

@RequiredArgsConstructor
public class MapPlacer {
    @Getter
    private final GameMap map;
    private final WorldAccessor worldAccessor;
    private final StructureManager structureManager;

    public void place(boolean placeBedrock, boolean fillStone) {
        Point size = map.getField().getSize();
        if (placeBedrock) {
            fillBedrock(size);
        }
        if (fillStone) {
            worldAccessor.fill(0, 0, 0, size.x() - 1, size.y() - 1, size.z() - 1, Material.STONE);
        }
        for (Room room: map.getGraph().getRooms()) {
            room.place(worldAccessor, structureManager);
        }
        Collection<PartTunnel> allTunnels = map.getGraph().getAllTunnels();
        allTunnels.stream()
                .filter(PartTunnel::isVertical)
                .forEach(partTunnel -> partTunnel.place(worldAccessor, structureManager));
        allTunnels.stream()
                .filter(partTunnel -> !partTunnel.isVertical())
                .forEach(partTunnel -> partTunnel.place(worldAccessor, structureManager));
        allTunnels.forEach(partTunnel -> partTunnel.afterPlace(worldAccessor));
    }

    private void fillBedrock(Point size) {
        worldAccessor.fill(-1, -1, -1, -1, size.y(), size.z(), Material.BEDROCK);
        worldAccessor.fill(-1, -1, -1, size.x(), size.y(), -1, Material.BEDROCK);
        worldAccessor.fill(-1, -1, -1, size.x(), -1, size.z(), Material.BEDROCK);
        worldAccessor.fill(size.x(), size.y(), size.z(), size.x(), -1, -1, Material.BEDROCK);
        worldAccessor.fill(size.x(), size.y(), size.z(), -1, -1, size.z(), Material.BEDROCK);
        worldAccessor.fill(size.x(), size.y(), size.z(), -1, size.y(), -1, Material.BEDROCK);
    }
}
