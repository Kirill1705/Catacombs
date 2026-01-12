package thor.catacombs.generator.map;

import thor.catacombs.generator.map.bind.TunnelGenerator;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public class CatacombsGenerator implements MapGenerator {
    private final RoomGraph rooms;
    @Override
    public BlockPosition getSize() {
        return rooms.getMapSize();
    }
    @Override
    public ImmutableGraph getStructuresGraph() {
        return rooms;
    }

    @Override
    public GameMap createMap(BlockLocation location) {
        return new GameMap(rooms, location);
    }

    public CatacombsGenerator(TunnelGenerator tunnelCreator, RoomGenerator roomGenerator) {
        rooms = roomGenerator.generate();
        tunnelCreator.generateTunnels(rooms);
        fixComponents();
    }
    private void fixComponents() {
        //implement later
    }
}

