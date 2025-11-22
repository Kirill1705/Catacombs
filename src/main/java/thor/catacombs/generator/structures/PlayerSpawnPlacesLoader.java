package thor.catacombs.generator.structures;

import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.generator.PlayerSpawnNode;
import thor.catacombs.info.block.PlayerSpawnInfo;
import thor.catacombs.info.structure.interfaces.PlayerSpawnableStructureInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PlayerSpawnPlacesLoader {
    private final List<PlayerSpawnNode> playerSpawnPlaces = new ArrayList<>();
    public PlayerSpawnPlacesLoader(Collection<PlayerSpawnInfo> playerSpawnable, BlockPosition position) {
        for (PlayerSpawnInfo playerInfo : playerSpawnable) {
            playerSpawnPlaces.add(new PlayerSpawnNode(playerInfo, position));
        }
    }
    public Collection<PlayerSpawnNode> getPlayerSpawnPlaces() {
        return Collections.unmodifiableCollection(playerSpawnPlaces);
    }
}
