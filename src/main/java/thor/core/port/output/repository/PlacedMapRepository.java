package thor.core.port.output.repository;

import thor.core.port.mapping.LocationDto;
import thor.core.port.mapping.dto.map.PlacedMapDto;

import java.util.Optional;
import java.util.UUID;

public interface PlacedMapRepository {
    void addMapOrReplace(PlacedMapDto mapDto);

    Optional<PlacedMapDto> findById(UUID placedMapId);

    Optional<PlacedMapDto> findByLocation(LocationDto locationDto);
}
