package thor.infrastructure.repositories;

import thor.core.port.mapping.dto.map.InteractiveGameMap;
import thor.core.port.output.repository.MapRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class MapRepositoryImpl implements MapRepository {
    private final Map<UUID, InteractiveGameMap> data = new HashMap<>();

    @Override
    public void addMap(InteractiveGameMap map) {
        data.put(map.id(), map);
    }

    @Override
    public Optional<InteractiveGameMap> findById(UUID mapId) {
        return Optional.ofNullable(data.getOrDefault(mapId, null));
    }

    @Override
    public void delete(UUID mapId) {
        data.remove(mapId);
    }
}
