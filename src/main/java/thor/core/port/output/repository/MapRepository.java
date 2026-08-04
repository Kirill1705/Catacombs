package thor.core.port.output.repository;

import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.port.mapping.dto.MapPartInfo;
import thor.core.port.mapping.dto.map.InteractiveGameMap;

import java.util.Optional;
import java.util.UUID;

public interface MapRepository {
    void addToIndex(UUID placedMapId, ImmutableBox box, String worldName);

    Optional<MapPartInfo> findByLocation(Point position, String worldName);

    void addMap(InteractiveGameMap map);

    Optional<InteractiveGameMap> findById(UUID mapId);

    void delete(UUID mapId);
}
