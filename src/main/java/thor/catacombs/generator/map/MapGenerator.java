package thor.catacombs.generator.map;

import thor.catacombs.game.Game;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public interface MapGenerator {
    BlockPosition getSize();
    ImmutableGraph getStructuresGraph();
    GameMap createMap(BlockLocation location);
}
