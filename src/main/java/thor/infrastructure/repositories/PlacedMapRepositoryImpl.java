package thor.infrastructure.repositories;

import ch.ethz.globis.phtree.PhTreeSolid;
import thor.core.port.mapping.LocationDto;
import thor.core.port.mapping.dto.map.PlacedMapDto;
import thor.core.port.output.repository.PlacedMapRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PlacedMapRepositoryImpl implements PlacedMapRepository {
    private final Map<UUID, PlacedMapDto> maps;

    private final PhTreeSolid<UUID> index;

    public PlacedMapRepositoryImpl() {
        maps = new HashMap<>();
        index = PhTreeSolid.create(3);
    }

    @Override
    public void addMapOrReplace(PlacedMapDto mapDto) {
        long[] newMin = {mapDto.corner1().x(), mapDto.corner1().y(), mapDto.corner1().z()};
        long[] newMax = {mapDto.corner2().x(), mapDto.corner2().y(), mapDto.corner2().z()};
        PhTreeSolid.PhIteratorS<UUID> iterator = index.queryIntersect(newMin, newMax);
        while (iterator.hasNext()) {
            var entry = iterator.nextEntry();
            delete(entry.value());
            index.remove(entry);
        }
        maps.put(mapDto.id(), mapDto);
        index.put(newMin, newMax, mapDto.id());
    }

    @Override
    public Optional<PlacedMapDto> findById(UUID placedMapId) {
        return Optional.ofNullable(maps.getOrDefault(placedMapId, null));
    }

    @Override
    public Optional<PlacedMapDto> findByLocation(LocationDto locationDto) {
        long[] min = {locationDto.x(), locationDto.y(), locationDto.z()};
        PhTreeSolid.PhIteratorS<UUID> iterator = index.queryIntersect(min, min);
        if (!iterator.hasNext()) {
            return Optional.empty();
        }
        UUID uuid = iterator.next();
        return findById(uuid);
    }

    private void delete(UUID uuid) {
        maps.remove(uuid);
    }
}
