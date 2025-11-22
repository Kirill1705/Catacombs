package thor.catacombs.generator.structures;

import thor.catacombs.generator.PlayerSpawnNode;

import java.util.Collection;

public interface PlayerSpawnable {
    Collection<PlayerSpawnNode> getPlayerSpawnPlaces();
}
