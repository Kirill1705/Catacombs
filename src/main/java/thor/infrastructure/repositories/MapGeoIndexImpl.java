package thor.infrastructure.repositories;

import ch.ethz.globis.phtree.PhTreeSolid;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.port.mapping.dto.MapPartInfo;
import thor.core.port.output.repository.MapGeoIndex;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class MapGeoIndexImpl implements MapGeoIndex {
    private final Map<String, PhTreeSolid<UUID>> index;

    public MapGeoIndexImpl() {
        index = new HashMap<>();
    }

    @Override
    public void addToIndex(UUID placedMapId, ImmutableBox box, String worldName) {
        if (!index.containsKey(worldName)) {
            index.put(worldName, PhTreeSolid.create(3));
        }
        long[] newMin = {box.begin().x(), box.begin().y(), box.begin().z()};
        long[] newMax = {box.end().x(), box.end().y(), box.end().z()};
        PhTreeSolid.PhIteratorS<UUID> iterator = index.get(worldName).queryIntersect(newMin, newMax);
        while (iterator.hasNext()) {
            var entry = iterator.nextEntry();
            index.get(worldName).remove(entry);
        }
        index.get(worldName).put(newMin, newMax, placedMapId);
    }

    @Override
    public Optional<MapPartInfo> findByLocation(Point position, String worldName) {
        if (!index.containsKey(worldName)) return Optional.empty();
        long[] min = {position.x(), position.y(), position.z()};
        PhTreeSolid.PhIteratorS<UUID> iterator = index.get(worldName).queryIntersect(min, min);
        if (iterator.hasNext()) {
            var node = iterator.nextEntry();
            return Optional.of(new MapPartInfo(node.value(), new Point((int) node.lower()[0], (int) node.lower()[1], (int) node.lower()[2])));
        }
        return Optional.empty();
    }
}
