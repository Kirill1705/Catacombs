package thor.core.port.output.repository;

import ru.vikhrenko.serverUtils.utils.dataStructures.BlockLocation;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.port.mapping.dto.map.PlacedMapDto;

import java.util.Optional;
import java.util.UUID;

public interface PlacedMapRepository {
    void addMapOrReplace(PlacedMapDto mapDto);

    Optional<PlacedMapDto> findById(UUID placedMapId);

    Optional<PlacedMapDto> findByLocation(Point position, String worldName);
}
