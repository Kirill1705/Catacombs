package thor.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.structure.Structure;
import thor.core.port.mapping.dto.PositionDto;
import thor.core.port.output.ArenaManager;
import thor.usefulUtils.utils.StructureUtils;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class ArenaManagerImpl implements ArenaManager {
    private final Path structurePath;
    private final BlockPosition size;

    private final Map<UUID, BlockLocation> locationMap;

    public ArenaManagerImpl(Path structurePath) {
        this.structurePath = structurePath;
        size = new Point(loadStructure(structurePath).getSize());
        locationMap = new HashMap<>();
    }

    @Override
    public void placeArena(PositionDto position, String worldName) {
        Structure structure = loadStructure(structurePath);
        StructureUtils.place(structure, new Location(Bukkit.getWorld(worldName), position.x(), 0, position.z()));
    }

    @Override
    public void tpPlayerToArena(UUID playerId, PositionDto position, String worldName) {
        World world = Bukkit.getWorld(worldName);
        final int maxAttemptCount = 10;
        for (int i = 0; i < maxAttemptCount; i++) {
            int x = (int) (position.x() + 1 + Math.random()*(size.x() - position.x() - 1));
            int y = (int) (position.y() + 1 + Math.random()*(size.y() - position.y() - 1));
            int z = (int) (position.z() + 1 + Math.random()*(size.z() - position.z() - 1));
            Location location = new Location(world, x, y, z);
            Block block = location.getBlock();
            Block upper = block.getRelative(0, 1, 0);
            if (i == maxAttemptCount - 1) {
                block.setType(Material.AIR);
                upper.setType(Material.AIR);
            }
            if (!block.isSolid() && !upper.isSolid()) {
                locationMap.put(playerId, new BlockLocation(location));
                Bukkit.getPlayer(playerId).teleport(location);
                return;
            }
        }
        log.warn("Cant tp player to arena");
    }

    @Override
    public boolean tpPlayerFromArena(UUID playerId) {
        if (!locationMap.containsKey(playerId)) {
            return false;
        }
        BlockLocation location = locationMap.get(playerId);
        Bukkit.getPlayer(playerId).teleport(location.toLocation());
        return true;
    }

    private Structure loadStructure(Path path) {
        try {
            return Bukkit.getStructureManager().loadStructure(path.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
