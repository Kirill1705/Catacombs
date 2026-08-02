package thor.core.port.output.repository;

import thor.core.port.mapping.dto.map.InteractiveGameMap;

import java.util.Optional;
import java.util.UUID;

public interface MapRepository {
    void addMap(InteractiveGameMap map);

    Optional<InteractiveGameMap> findById(UUID mapId);

    void delete(UUID mapId);
}
