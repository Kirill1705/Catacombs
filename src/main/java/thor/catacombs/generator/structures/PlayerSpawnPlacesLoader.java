package thor.catacombs.generator.structures;

import thor.catacombs.generator.PlayerSpawnNode;
import thor.catacombs.generator.structures.utils.StructureLocation;
import thor.catacombs.info.block.PlayerSpawnInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PlayerSpawnPlacesLoader {
    private final List<PlayerSpawnNode> playerSpawnPlaces = new ArrayList<>();
    public PlayerSpawnPlacesLoader(Collection<PlayerSpawnInfo> playerSpawnable, StructureLocation position) {
        for (PlayerSpawnInfo playerInfo : playerSpawnable) {
            playerSpawnPlaces.add(new PlayerSpawnNode(playerInfo, position));
        }
    }
    public Collection<PlayerSpawnNode> getPlayerSpawnPlaces() {
        return Collections.unmodifiableCollection(playerSpawnPlaces);
    }
}
