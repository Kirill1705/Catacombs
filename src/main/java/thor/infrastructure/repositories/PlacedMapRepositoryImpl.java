package thor.infrastructure.repositories;

import ch.ethz.globis.phtree.PhTreeSolid;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.port.mapping.dto.map.AllMapInfo;
import thor.core.port.mapping.dto.map.PlacedMapDto;
import thor.core.port.output.repository.PlacedMapRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PlacedMapRepositoryImpl implements PlacedMapRepository {
    private final Map<UUID, AllMapInfo> maps;

    private final PhTreeSolid<UUID> index;

    public PlacedMapRepositoryImpl() {
        maps = new HashMap<>();
        index = PhTreeSolid.create(3);
    }

    @Override
    public void addMapOrReplace(AllMapInfo mapDto) {
        PlacedMapDto placedMapDto = mapDto.mapDto();
        long[] newMin = {placedMapDto.corner1().x(), placedMapDto.corner1().y(), placedMapDto.corner1().z()};
        long[] newMax = {placedMapDto.corner2().x(), placedMapDto.corner2().y(), placedMapDto.corner2().z()};
        PhTreeSolid.PhIteratorS<UUID> iterator = index.queryIntersect(newMin, newMax);
        while (iterator.hasNext()) {
            var entry = iterator.nextEntry();
            delete(entry.value());
            index.remove(entry);
        }
        maps.put(placedMapDto.id(), mapDto);
        index.put(newMin, newMax, mapDto.mapDto().id());
    }

    @Override
    public Optional<AllMapInfo> findById(UUID placedMapId) {
        return Optional.ofNullable(maps.getOrDefault(placedMapId, null));
    }

    @Override
    public Optional<AllMapInfo> findByLocation(Point position, String worldName) {
        long[] min = {position.x(), position.y(), position.z()};
        PhTreeSolid.PhIteratorS<UUID> iterator = index.queryIntersect(min, min);
        if (iterator.hasNext()) {
            UUID uuid = iterator.next();
            if (maps.get(uuid).mapDto().worldName().equals(worldName)) {
                return Optional.of(maps.get(uuid));
            }
        }
        return Optional.empty();
    }

    private void delete(UUID uuid) {
        maps.remove(uuid);
    }
}
