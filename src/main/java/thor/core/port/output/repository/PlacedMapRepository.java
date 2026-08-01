package thor.core.port.output.repository;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.port.mapping.dto.map.AllMapInfo;

import java.util.Optional;
import java.util.UUID;

public interface PlacedMapRepository {
    void addMapOrReplace(AllMapInfo mapDto);

    Optional<AllMapInfo> findById(UUID placedMapId);

    Optional<AllMapInfo> findByLocation(Point position, String worldName);
}
