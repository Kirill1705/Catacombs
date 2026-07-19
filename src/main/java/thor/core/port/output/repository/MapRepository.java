package thor.core.port.output.repository;

import thor.core.generator.complete.GameMap;

import java.util.Optional;
import java.util.UUID;

public interface MapRepository {
    void addMap(GameMap map);

    Optional<GameMap> findById(UUID mapId);
}
