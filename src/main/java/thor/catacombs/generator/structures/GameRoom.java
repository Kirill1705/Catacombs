package thor.catacombs.generator.structures;

import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.generator.Exit;
import thor.catacombs.generator.ItemGeneratorHolder;
import thor.catacombs.generator.PlayerSpawnNode;
import thor.catacombs.info.structure.interfaces.RoomInfo;

import java.util.*;

public class GameRoom extends GameStructure implements Room {
    private final ExitLoader exitLoader;
    private final PlayerSpawnPlacesLoader spawnLoader;
    private final RoomInfo info;
    @Override
    public List<Exit> getExits() {
        return exitLoader.getExits();
    }

    @Override
    public RoomInfo getInfo() {
        return info;
    }

    public GameRoom(RoomInfo info, BlockPosition position, ItemGeneratorHolder generator) {
        super(info, position, generator);
        exitLoader = new ExitLoader(info.getExitsInfo(), position);
        spawnLoader = new PlayerSpawnPlacesLoader(info.getPlayerSpawnPlacesInfo(), position);
        this.info = info;
    }

    @Override
    public void place(BlockLocation location){
        super.place(location);
        for (Exit exit: getExits()) {
            exit.place(location);
        }
    }

    @Override
    public Collection<PlayerSpawnNode> getPlayerSpawnPlaces() {
        return spawnLoader.getPlayerSpawnPlaces();
    }
}
