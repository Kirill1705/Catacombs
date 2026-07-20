package thor.infrastructure.repositories;

import thor.core.generator.complete.GameMap;
import thor.core.port.output.repository.MapRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class MapRepositoryImpl implements MapRepository {
    private final Map<UUID, GameMap> data = new HashMap<>();

    @Override
    public void addMap(GameMap map) {
        data.put(map.getUuid(), map);
    }

    @Override
    public Optional<GameMap> findById(UUID mapId) {
        return Optional.ofNullable(data.getOrDefault(mapId, null));
    }

    @Override
    public void delete(UUID mapId) {
        data.remove(mapId);
    }
}
