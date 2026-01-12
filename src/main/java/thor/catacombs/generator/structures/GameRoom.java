package thor.catacombs.generator.structures;

import thor.catacombs.generator.Exit;
import thor.catacombs.generator.ItemGeneratorHolder;
import thor.catacombs.generator.PlayerSpawnNode;
import thor.catacombs.generator.structures.utils.GameWorldAccessor;
import thor.catacombs.generator.structures.utils.StructureLocation;
import thor.catacombs.info.structure.interfaces.RoomInfo;

import java.util.Collection;
import java.util.List;

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

    public GameRoom(RoomInfo info, StructureLocation position, ItemGeneratorHolder generator) {
        super(info, position, generator);
        exitLoader = new ExitLoader(info.getExitsInfo(), position);
        spawnLoader = new PlayerSpawnPlacesLoader(info.getPlayerSpawnPlacesInfo(), position);
        this.info = info;
    }

    @Override
    public void place(GameWorldAccessor accessor){
        super.place(accessor);
        for (Exit exit: getExits()) {
            exit.place(accessor);
        }
    }

    @Override
    public Collection<PlayerSpawnNode> getPlayerSpawnPlaces() {
        return spawnLoader.getPlayerSpawnPlaces();
    }
}
