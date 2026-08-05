package thor.core.port.output.repository;

import thor.core.generator.complete.GeneratedGameMap;

import java.util.Optional;
import java.util.UUID;

public interface MapRepository {
    Optional<GeneratedGameMap> findById(UUID mapId);

    UUID add(GeneratedGameMap gameMap);

    void delete(UUID mapId);
}
