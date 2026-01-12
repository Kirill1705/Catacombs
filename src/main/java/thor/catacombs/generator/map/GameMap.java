package thor.catacombs.generator.map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import thor.catacombs.generator.PlayerSpawnNode;
import thor.catacombs.generator.structures.PlayerSpawnable;
import thor.catacombs.generator.structures.Structure;
import thor.catacombs.generator.structures.TunnelPart;
import thor.catacombs.generator.structures.utils.GameWorldAccessor;
import thor.catacombs.info.structure.TunnelType;
import thor.usefulUtils.utils.OtherUtils;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.ImmutableBox;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.List;
import java.util.stream.Collectors;

public class GameMap {
    private final RoomGraph graph;
    private final GameWorldAccessor accessor;

    public GameMap(RoomGraph graph, BlockLocation location) {
        this.graph = graph;
        this.accessor = new GameWorldAccessor(new ImmutableBox(location, location.add(graph.getMapSize()).subtract(new Point(1, 1, 1))));
    }

    public void placeBedrock() {
        BlockPosition size = graph.getMapSize();
        OtherUtils.fill(createBox(1, 1, 1, size.x() - 1, size.y() - 1, size.z() - 1), Material.STONE);
        OtherUtils.fill(createBox(0, 0, 0, size.x(), size.y(), 0), Material.BEDROCK);
        OtherUtils.fill(createBox(0, 0, 0, size.x(), 0, size.z()), Material.BEDROCK);
        OtherUtils.fill(createBox(0, 0, 0, 0, size.y(), size.z()), Material.BEDROCK);
        OtherUtils.fill(createBox(size.x(), size.y(), size.z(), 0, size.y(), 0), Material.BEDROCK);
        OtherUtils.fill(createBox(size.x(), size.y(), size.z(), size.x(), 0, 0), Material.BEDROCK);
        OtherUtils.fill(createBox(size.x(), size.y(), size.z(), 0, 0, size.z()), Material.BEDROCK);
    }

    public void place() {
        placeBedrock();
        List<Structure> structures = graph.getAllStructures();
        structures.stream()
                .filter(this::isVertical)
                .forEach(structure -> structure.place(accessor));
        structures.stream()
                .filter(structure -> !isVertical(structure))
                .forEach(structure -> structure.place(accessor));
        structures.stream()
                .filter(structure -> structure instanceof AfterPlacing)
                .forEach(structure -> ((AfterPlacing) structure).afterPlace(accessor));
    }

    private boolean isVertical(Structure structure) {
        return structure instanceof TunnelPart part && part.getInfo().getType() == TunnelType.VERTICAL;
    }

    private List<PlayerSpawnNode> getPlayerSpawnPlaces() {
        return graph.getAllStructures().stream()
                .filter(structure -> structure instanceof PlayerSpawnable)
                .map(structure -> (PlayerSpawnable)structure)
                .flatMap(playerSpawnable -> playerSpawnable.getPlayerSpawnPlaces().stream())
                .collect(Collectors.toList());
    }

    public boolean spawnPlayers(Iterable<Player> players) {
        List<PlayerSpawnNode> playerSpawnNodes = getPlayerSpawnPlaces();
        if (playerSpawnNodes.isEmpty())
            return false;
        for (Player player : players) {
            int beginIdx = (int) (Math.random()*playerSpawnNodes.size());
            boolean isSpawned = false;
            for (int i = 0; i < playerSpawnNodes.size(); i++) {
                int idx = (beginIdx+i)%playerSpawnNodes.size();
                if (!playerSpawnNodes.get(idx).isClosed()) {
                    playerSpawnNodes.get(idx).spawnPlayer(accessor, player);
                    isSpawned = true;
                    break;
                }
            }
            if (!isSpawned) {
                playerSpawnNodes.getFirst().spawnPlayer(accessor, player);
            }
        }
        return true;
    }

    private ImmutableBox createBox(int x1, int y1, int z1, int x2, int y2, int z2) {
        BlockLocation location = accessor.begin();
        return new ImmutableBox(location.add(new Point(x1, y1, z1)), location.add(new Point(x2, y2, z2)));
    }
}
