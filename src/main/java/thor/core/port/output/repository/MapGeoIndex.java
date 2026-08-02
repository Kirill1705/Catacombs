package thor.core.port.output.repository;

import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.port.mapping.dto.MapPartInfo;

import java.util.Optional;
import java.util.UUID;

public interface MapGeoIndex {
    void addToIndex(UUID placedMapId, ImmutableBox box, String worldName);

    Optional<MapPartInfo> findByLocation(Point position, String worldName);
}
