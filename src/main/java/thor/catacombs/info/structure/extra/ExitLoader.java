package thor.catacombs.info.structure.extra;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import thor.catacombs.info.block.ExitPosition;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.block.ExitInfo;
import thor.catacombs.info.structure.interfaces.ExitableStructureInfo;
import thor.usefulUtils.utils.OtherUtils;
import thor.usefulUtils.utils.dataStructures.ImmutableBox;
import thor.usefulUtils.utils.dataStructures.Point;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class ExitLoader implements ExitableStructureInfo {
    private final List<ExitInfo> exits = new ArrayList<>();
    private PointPosition countEnds(BlockLocation location, ImmutableBox box) {
        Coordinates x = new Coordinates(location.x(), box.begin().x(), box.end().x());
        Coordinates y = new Coordinates(location.y(), box.begin().y(), box.end().y());
        Coordinates z = new Coordinates(location.z(), box.begin().z(), box.end().z());
        int coincidence = x.isEqual() + y.isEqual() + z.isEqual();
        return PointPosition.values()[coincidence];
    }

    private Material readMaterial(BlockLocation atPoint, Set<BlockLocation> blocks) {
        BlockLocation current = atPoint;
        boolean success = true;
        while (blocks.contains(current)) {
            current.add(new Point(0, 1, 0));
            if (current.toLocation().getBlock().getType() == Material.AIR) {
                success = false;
                break;
            }
        }
        if (!success) {
            current = atPoint.add(new Point(1, 0, 0));
        }
        return current.toLocation().getBlock().getType();
    }

    private FindExitStatsResult getExitStats(Set<BlockLocation> blocks) {
        int y = blocks.iterator().next().y();
        List<BlockLocation> downBlocks = blocks.stream()
                .filter(location -> location.y() == y)
                .sorted(Comparator.comparingInt(o -> o.x() + o.z())).toList();
        BlockLocation atPoint = downBlocks.get(downBlocks.size()/2);

        return new FindExitStatsResult(atPoint, readMaterial(atPoint, blocks));
    }

    private @NotNull Set<BlockLocation> getExitBlocks(BlockLocation first, Set<BlockLocation> visited, ImmutableBox box) {
        if (ExitInfo.isCollidable(first.toLocation().getBlock())) {
            return Set.of();
        }
        TreeSet<BlockLocation> result = new TreeSet<>(Comparator.comparingInt(BlockLocation::y));
        result.add(first);
        OtherUtils.bfsBlocks(first, location -> {
            visited.add(location);
            if (countEnds(location, box).ordinal()>PointPosition.EXIT.ordinal()) {
                result.clear();
                return List.of();
            }
            if (!result.isEmpty()) {
                result.add(location);
            }
            return neighbors(location, box);
        });
        return result;
    }

    private Collection<BlockLocation> neighbors(BlockLocation location, ImmutableBox box) {
        BlockPosition[] offsets = new BlockPosition[]{new Point(1, 0, 0), new Point(-1, 0, 0), new Point(0, 1, 0), new Point(0, -1, 0), new Point(0, 0, 1), new Point(0, 0, -1)};
        return Arrays.stream(offsets)
                .map(location::add)
                .filter(loc -> !ExitInfo.isCollidable(loc.toLocation().getBlock())&&countEnds(loc, box)!=PointPosition.INSIDE).toList();
    }

    public ExitLoader(ImmutableBox box, AttributeRegistry registry, Collection<ExitPosition> exitPositions) {
        Set<BlockLocation> visited = new HashSet<>();
        for (ExitPosition position: exitPositions) {
            BlockLocation atPoint = box.begin().add(position.getPosition());
            Set<BlockLocation> blocks = getExitBlocks(atPoint, visited, box);
            exits.add(new ExitInfo(
                    position.getConfig(),
                    readMaterial(atPoint, blocks),
                    position.getPosition(),
                    box.size(),
                    registry,
                    blocks.stream()
                            .<BlockPosition>map(location -> location.subtract(box.begin()))
                            .toList()
            ));
        }
        for (Block block: box.blockIterable()) {
            BlockLocation location = new BlockLocation(block.getLocation());
            if (!visited.contains(location)&&countEnds(location, box)==PointPosition.EXIT) {
                Set<BlockLocation> blocks = getExitBlocks(location, visited, box);
                if (!blocks.isEmpty()) {
                    FindExitStatsResult result = getExitStats(blocks);
                    exits.add(new ExitInfo(
                            new YamlConfiguration(),
                            result.material,
                            result.atPoint.subtract(box.begin()),
                            box.size(),
                            registry,
                            blocks.stream()
                                    .<BlockPosition>map(loc -> loc.subtract(box.begin()))
                                    .toList()
                    ));
                }
            }
        }
    }

    @Override
    public Collection<ExitInfo> getExitsInfo() {
        return exits;
    }

    private enum PointPosition {
        INSIDE,
        EXIT,
        EDGE,
        ANGLE
    }

    private record Coordinates(int locCord, int beginCord, int endCord) {
        public int isEqual() {
            if (locCord == beginCord || locCord == endCord) {
                return 1;
            }
            return 0;
        }
    }

    private record FindExitStatsResult(BlockLocation atPoint, Material material) {}
}
