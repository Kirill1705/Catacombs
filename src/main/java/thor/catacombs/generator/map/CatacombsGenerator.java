package thor.catacombs.generator.map;

import thor.catacombs.generator.map.bind.TunnelGenerator;
import thor.catacombs.generator.structures.*;
import thor.catacombs.info.structure.*;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

import java.util.*;

public class CatacombsGenerator implements MapGenerator{
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

