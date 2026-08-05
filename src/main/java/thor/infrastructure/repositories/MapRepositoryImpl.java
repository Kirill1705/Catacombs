package thor.infrastructure.repositories;

import thor.core.generator.complete.GameMap;
import thor.core.generator.complete.GeneratedGameMap;
import thor.core.port.output.repository.MapRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class MapRepositoryImpl implements MapRepository {
    private final Map<UUID, GeneratedGameMap> data = new HashMap<>();

    @Override
    public Optional<GeneratedGameMap> findById(UUID mapId) {
        return Optional.ofNullable(data.getOrDefault(mapId, null));
    }

    @Override
    public UUID add(GeneratedGameMap gameMap) {
        UUID id = UUID.randomUUID();
        data.put(id, gameMap);
        return id;
    }

    @Override
    public void delete(UUID mapId) {
        data.remove(mapId);
    }
}
